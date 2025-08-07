# Animation Resolver System (ARS) - CA's in-house animation API
## Fundamental Definition
We're basically just writing a system where modelers and animators can use either GeckoLib or Vanilla (with our own BlockBench plugin that effectively allows for exporting said Vanilla animations to JSON) format, then push that into CA. The goal for ARS is to be able to parse those models/animations correctly (with proper feature support, see "Format-Based Animation/Model Features" below) and pass them through a functional pipeline that enables them to be dynamic while remaining optimized.

## Pipeline: Rudimentary Concept
### GeckoLib || Vanilla -> FAAL (Functional Animation Abstraction Layer)
The specific formats mentioned here are GeckoLib and Vanilla. Both formats perform rendering separately as their own sub-systems, but they first must pass all of their data to FAAL through their parsers before FAAL passes data back to the client for rendering. The aforementioned pipeline, on a high level, goes something like this:
- The animations and models are located inside of CA's datapack and parsed into their own generic objects with generic meta/data (bone hierarchy in model files would be stored as multimaps within a generic holder ``record``, animation length would be stored as a ``double``, etc.).
- These objects are then passed into FAAL for caching and processing. At this point, animations whose builder objects are specified to be baked (see ``AnimationManager`` for more info) would be ready for playback (besides the usual processing needed beforehand, as well as dynamic easing and such, which is handled by the client), whereas "dynamic" animations would need to undergo further processing based on their configs (for instance, if IK should be applied to an animation, or the whole model, etc.)
- FAAL would then proceeed to handle updating data (MoLang, animation progress, etc.) and pass that information lazily to the client for rendering. The client would use predictive interpolation in order to render animations beyond the maximum 20 TPS of a typical server (20 would be treated as the standard, and animations would thus naturally scale in speed if the TPS is lower/higher somehow).
- Every time a server reload is performed, clients would save their last recorded state while the reload happens, then snap to the updated data (if any) post-reload.

TL;DR Parse -> pass -> cache -> process -> update -> handle edge cases -> save (on quit) if possible

## Format-Based Animation/Model Features
In a nutshell:

### GeckoLib
- MoLang
- Linear, Catmullrom (smooth),  and Step easing types, each supporting individual keyframe easings added by the GeckoLib BlockBench plugin (e.g. ease-in-out quad, eas-out quad, ease-in cubic, etc. -> Each easing here would have ease-in, ease-out, and ease-in-out variants, with the exception of Step and Catmullrom, since they're supercategorical keyframe easing types rather than easings themselves).
- Exported to JSON in standard format, as provided by the official plugin.

### Vanilla ("Plus")
- Linear, Catmullrom (smooth), Bezier (JSON would include additional parameters specifying control points for the spline), and Step easing types.
- Exported to JSON via our own BlockBench plugin (not really difficult, just create a plugin that grabs animation and model info, then parses that accordingly).

## Implementation
- I'll start with parser implementations for both GeckoLib and Vanilla. I'll finish GeckoLib first (since it has a plugin already), then move onto Vanilla by creating a BlockBench plugin that can export animations and models to JSON from the "Modded Entity" format before writing the parser for it as well.
- I'll then implement FAAL, where parsed objects would go. I'll write the caching and processing logic first based on our own custom animation builder objects to allow for both baked and dynamic animations, starting off with baked ones ("baked" here is kind of a misnomer since MoLang would still have to be applied through state updates) and then writing dynamic ones.
- I will then write the logic for caching and processing models' bones, then converting them into bounding boxes (once again, based on our own builder objects). I'll be sure to keep things as optimized as I can.
- If time allows, I will proceed with writing the IK solver. This is not really a priority, but it would go a very long way in terms of keeping things fresh. TRhis is an optional feature for 0.13.0.0 and does not have to be present during this update.

## ETA?
Idk, I will probably update this later tho depending on where I'm at and at what time and whatnot.