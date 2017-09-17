(
  postln("start loading util");
  ~scale_frequency = {| amount, start = 20, stop = 20000, n = 1 |
    start * (stop/start).pow(amount/n);
  };

  ~exponential_interp_gen = {
    | out_min = 0.01, out_max = 1, in_min = 0, in_max = 127 |

    var in_range = in_max - in_min;

    { | val |
      var in_proportion = (val - in_min) / in_range;
      out_min * (out_max / out_min).pow(in_proportion);
    }
  };

  ~scale_freq = ~exponential_inter_gen.(20, 20000, 0, 1);

  ~linear_interp_gen = {
    | out_min = 0, out_max  = 1, in_min = 0, in_max = 127|

    var out_range = out_max - out_min,
        in_range = in_max - in_min;

    { | val |
      var in_proportion = (val - in_min) / in_range;
      (in_proportion * out_range) + out_min;
    }
  };

  ~events = Dictionary.new;
  ~midi_controls = Dictionary.new;

  ~load_synth = { |synth_name|
    postln("loading synth" + synth_name);
    ~current_synth = synth_name;
    ~current_event = ~events.at(synth_name);
    ~current_midi_controls = ~midi_controls.at(synth_name);
    this.executeFile(Platform.userConfigDir +/+ 'src/midi/simple_midi.sc');
  };
  postln("done loading util");
)
