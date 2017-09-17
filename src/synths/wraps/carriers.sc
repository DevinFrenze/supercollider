(
  ~carriers = Dictionary[
    \sine   -> {| freq = 440, phase = 0 | SinOsc.ar(freq, phase) },
    \pulse  -> {| freq = 400, width = 0.5 | Pulse.ar(freq, width) },
    \saw    -> {| freq = 440 | Saw.ar(freq) },
    \blip   -> {| freq = 400, numHarmonics = 200 | Blip.ar(freq, numHarmonics) }
  ];

  ~events.put(\sine,  ( \phase: 0 ));
  ~events.put(\pulse, ( \width: 0.5 ));
  ~events.put(\blip,  ( \numHarmonics: 200 ));

  ~midi_controls.put(\sine,  [ \phase -> ~linear_interp_gen.(0, 2pi) ]);
  ~midi_controls.put(\pulse, [ \width -> ~linear_interp_gen.() ]);
  ~midi_controls.put(\blip,  [ \num_harm -> ~exponential_interp_gen.(1, 200) ]);
)
