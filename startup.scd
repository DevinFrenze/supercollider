Server.local.boot;
Server.local.makeWindow;
MIDIClient.init;
MIDIIn.connectAll;
this.executeFile(Platform.userConfigDir +/+ 'src/synths/simple_synths.sc');
~defaultSynthName = \simple_pulse;
this.executeFile(Platform.userConfigDir +/+ 'src/midi/default_midi.sc');
