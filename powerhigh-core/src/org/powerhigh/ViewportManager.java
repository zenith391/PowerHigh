package org.powerhigh;

import java.util.HashMap;
import java.util.Map;

import org.powerhigh.graphics.Interface;
import org.powerhigh.utils.Area;

public abstract class ViewportManager {

	protected Map<String, Object> props = new HashMap<>();
	
	public abstract Area getViewport(Interface win);
	
	public Map<String, Object> getSpecialProperties() {
		return props;
	}

}
