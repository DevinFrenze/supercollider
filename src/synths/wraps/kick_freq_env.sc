(
  ~kick_freq_env = {
    | freq, kick_freq_range = 1, kick_freq_decay = 0.2, kick_freq_curve = -4 |
    var envelopeLevels = [1, 0],
        envelopeTimes = [kick_freq_decay],
        envelope = Env.new(envelopeLevels, envelopeTimes, kick_freq_curve),
        freq_scale = freq * 2.pow(kick_freq_range - 1),
        freq_offset = freq;
    EnvGen.ar(envelope, 1, freq_scale, freq_offset);
  };

  ~events.put(\kick_freq_env, (
    kick_freq_decay: 0.2,
    kick_freq_range: 2
  ));

  ~midi_controls.put(\kick_freq_env, [
    \kick_freq_decay -> ~exponential_interp_gen.(0.01, 0.25),
    \kick_freq_range -> ~linear_interp_gen.(0, 6)
  ]);
)
