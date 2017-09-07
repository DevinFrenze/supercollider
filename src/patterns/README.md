# Pattern reference
* use `Pattern.asStream.all;` to view the execution of the pattern
* use `Pbind( key_pairs )` to create event stream
* use `Pbindf( parent_event_stream, key_pairs)` to nest extend event streams
* use `Pchain( stream_one, stream_two)` to add stream_one onto (and over) stream_two
* use `Ppar( list_of_streams )` to play multiple streams in parallel
** use `Ppar( list_of_delays_and_streams )` to offset some streams
