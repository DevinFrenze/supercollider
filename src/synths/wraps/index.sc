(
  this.executeFile(Platform.userConfigDir +/+ 'src/synths/wraps/carriers.sc');
  this.executeFile(Platform.userConfigDir +/+ 'src/synths/wraps/amp_adsr.sc');
  this.executeFile(Platform.userConfigDir +/+ 'src/synths/wraps/rlpf.sc');
  this.executeFile(Platform.userConfigDir +/+ 'src/synths/wraps/filter_freq_adsr.sc');

  ~stereo = { | signal, out = 0, pan = 0, amp = 1 |
    Out.ar(out, Pan2.ar(signal, pan, amp))
  };
)
