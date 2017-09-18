(
  var path = Platform.userConfigDir ++ '/src/synths/wraps/';

  this.executeFile(path ++ 'carriers.sc');
  this.executeFile(path ++ 'amp_adsr.sc');
  this.executeFile(path ++ 'rlpf.sc');
  this.executeFile(path ++ 'filter_freq_adsr.sc');

  ~stereo = { | signal, out = 0, pan = 0, amp = 1 |
    Out.ar(out, Pan2.ar(signal, pan, amp))
  };
)
