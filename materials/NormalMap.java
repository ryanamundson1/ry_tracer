package materials;

import ryTracer.Color;
import Tracer.HitRecord;

public class NormalMap extends Material {

	public NormalMap(Long m) {
		matId = m;
	}

	@Override
	public Color calcColor(HitRecord hit) {
		return new Color (Math.sqrt(hit.normal.x*hit.normal.x), Math.sqrt(hit.normal.y*hit.normal.y), Math.sqrt(hit.normal.z*hit.normal.z));
	}

}
