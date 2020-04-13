/**
 * JavaFX rendering module. Automatically imports <code>powerhigh-core</code> if not present.
 */
module powerhigh.jfx {
	requires transitive javafx.controls;
	requires transitive powerhigh.core;
	
	exports org.powerhigh.jfx;
	exports org.powerhigh.jfx.input;
}