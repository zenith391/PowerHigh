PROCESS_PIXELS = true
PROCESS_DOWNSCALE = [640, 480] // downscale to avoid having to treat a 1920x1080 screen, which depending on implementation, might be very laggy
// this should be ignored on implementations that have hardware shaders.

function color(x, y, red, green, blue) {
	return [red+x%255, green+y%255, blue+x+y%255];
}