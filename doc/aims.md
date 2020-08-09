# Aims

Where do we want to go?

- Classical engineering disiplines currently lack intermediary data formats for
  analyses, and the ability to track what they do as they go.
- We would like to prototype with "such tools" - can we get a digital workflow
  going? To gain feedback on what works, we want to dogfood.
- Results we want from the dogfooding:
  - Simple sharing of analyses; give a link to someone and let them browse a
    piece of stable results
  - Play with an interactive format for working with physics

This is a whole lot of stuff. How do we order it?

- Good entry point: Present that dashboard in a way that's accessible. The
  project dashboard!
- Good entry point: Create a Matchcad-like that runs in the browser and lets you
  connect / disconnect source data. Think package management. Perhaps what we're
  creating is a package manager?

A thesis on constraints:

- We _do not want_ a full-blown programming language. If it's full blown, it's
  sufficiently user friendly. We want fast updates and determinism, not turing
  completeness.
- We _do want_ plugins with full-featured programming language features. Just,
  they have to conform to reactivity requirements themselves, update themselves.
  We could support Javascript plugins that "run directly", or external
  applications. "Plain plugins" could also work.

Is this like Nextjournal?

> Nextjournal implements full-featured programming in the browser. They span
> _very_ broadly. We want to take on a subset of that challenge.

Do we need to start prototyping both frontend and backend?

> Yes, most likely. Welcome: the bifrost. Go.
