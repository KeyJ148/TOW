package cc.abro.tow.client.map.specification;

import java.util.Map;

public record MapObjectSpecification(int id, int x, int y, int z, String type, Map<String, Object> parameters) { }
