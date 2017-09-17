(
  ~rlpf = {| in, filter_freq = 440, rq = 1 |
    RLPF.ar(in, filter_freq.clip(0, 20000), rq)
  };

  ~midi_controls.put(\rlpf, [
    \rq -> ~exponential_interp_gen.(),
    \filter_freq -> ~exponential_interp_gen.(20, 20000)
  ]);

  ~events.put(\rlpf, (
    rq: 1,
    filter_freq: 20000
  ));
)
