(
  var pattern = Pbind(
    \instrument, \kick,
    \tempo, 120 / 60,
    \kick_freq_decay, 0.2,
    \kick_freq_range, 2,
    \attack, 0.01,
    \release, 0.3,
    \midinote, 19 + Pseq([Pn(0, 12), 12, 12, 7, 7], inf),
    \dur, Pseq([1, 1, 0.75, 1.25], inf),
    \amp, 1
  );
  pattern.play();
  ~load_synth.("fm_sine_by_sine_2_times");
)

Pn(19,4) + Pn(19, 4);
