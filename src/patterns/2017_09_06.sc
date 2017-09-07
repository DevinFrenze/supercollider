(
  var main_pattern = Pbind(
    \tempo, 1.5,
    \scale, Scale.minor,
  ),

  p1 = Pbindf( main_pattern,
    \instrument, \simple_pulse,
    \degree, Pseq([0,1,2,1,2,3,2,1], inf) + 3,
    \root, 0,
    \dur, 0.25,
  ),

  a = Pbind(
    \filter_attack, 0.1,
    \filter_decay, 0.03,
    \filter_sustain, 0.33,
    \filter_peak, 0.2,
    \width, 0.5,
    \rq, 1.5,
    \amp, Pseq([Pn(0.5, 7), 0], inf),
  ),

  b = Pbind(
    \filter_attack, 0.1,
    \filter_decay, 0.05,
    \filter_sustain, 0.5,
    \filter_peak, 0.25,
    \width, 0.4,
    \rq, 2,
    \amp, 0.25
  );

  c = Pbindf(a,
    \degree, 3,
    // \delta, Pseq([Pn(2, 8), Pseq([0.125, 2 - 0.125], 16)], inf),
    \delta, Pseq([Pn(0.125, 12), 2 - (0.125 * 12)], 16),
    \dur, 0.25,
    \amp, 0.5,
    \filter_attack, 0.02,
    \pan, Pstutter(Pseq([2,2,3,2,2,1], inf), Pxrand((-1..1), inf))
  );

  Ppar([
    Pchain(a, p1),
    Pchain(b, p1),
    Pchain(c, p1)
  ]).play();
)
