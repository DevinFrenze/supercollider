(
  var carriers_events, carriers_midi_controls;

  ~carriers = Dictionary[
    \sine   -> {| freq = 440, phase = 0 | SinOsc.ar(freq, phase) },
    \pulse  -> {| freq = 400, width = 0.5 | Pulse.ar(freq, width) },
    \saw    -> {| freq = 440 | Saw.ar(freq) },
    \blip   -> {| freq = 400, num_harmonics = 200 | Blip.ar(freq, num_harmonics) }
  ];

  carriers_events = Dictionary.new;
  carriers_events.put(\sine,  ( \phase: 0 ));
  carriers_events.put(\saw,   ( ));
  carriers_events.put(\pulse, ( \width: 0.5 ));
  carriers_events.put(\blip,  ( \num_harmonics: 200 ));
  ~events.put(\carriers, carriers_events);

  carriers_midi_controls = Dictionary.new;
  carriers_midi_controls.put(\sine,  [ \phase -> ~linear_interp_gen.(0, 2pi) ]);
  carriers_midi_controls.put(\saw,   [ ]);
  carriers_midi_controls.put(\pulse, [ \width -> ~linear_interp_gen.() ]);
  carriers_midi_controls.put(\blip,  [ \num_harmonics -> ~exponential_interp_gen.(1, 200) ]);
  ~midi_controls.put(\carriers, carriers_midi_controls);
)
