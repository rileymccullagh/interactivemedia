DrumMachine dm;
BassSynth bass;
int i = 0;
void setup() {
  String[] patterns = {"10001000","00000000", "0", "00001000", "0", "0", "0", "0", "00100010"};
  //dm = new DrumMachine(this, patterns);
  bass = new BassSynth(this);
}

void draw() {
  bass.trigger();
  delay(500);
}
