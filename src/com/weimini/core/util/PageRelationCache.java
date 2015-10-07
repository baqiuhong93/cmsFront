package com.weimini.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PageRelationCache {
	
	private static Map<Integer,Set<Integer>> relationNodes = Collections.synchronizedMap(new HashMap<Integer,Set<Integer>>());
	
	private static Map<Integer,Set<Integer>> relationFragments = Collections.synchronizedMap(new HashMap<Integer,Set<Integer>>());
	
	private static PageRelationCache updatedRelationCache = null;
	
	private PageRelationCache() {
	}	
	
	public static PageRelationCache getInstance() {
		if (updatedRelationCache == null) {
			updatedRelationCache = new PageRelationCache();
		}
		return updatedRelationCache;
	}
	
	public void addNodes(int nodeId, Set<Integer> nodeIds) {
		if(relationNodes.containsKey(nodeId)) {
			Set<Integer> existNodeIds = relationNodes.get(nodeId);
			existNodeIds.addAll(nodeIds);
		} else {
			relationNodes.put(nodeId, nodeIds);
		}
	}
	
	public void addFragment(int nodeId, int fragmentId) {
		if(relationFragments.containsKey(nodeId)) {
			Set<Integer> existFragments = relationFragments.get(nodeId);
			existFragments.add(fragmentId);
		} else {
			Set<Integer> fragmentIds = new HashSet<Integer>();
			fragmentIds.add(fragmentId);
			relationFragments.put(nodeId, fragmentIds);
		}
	}
	
	public Map<Integer,Set<Integer>> getRelationNodes() {
		return relationNodes;
	}
	
	public Map<Integer,Set<Integer>> getRelationFragments() {
		return relationFragments;
	}
	
	public void clearRelationNodes() {
		relationNodes.clear();
	}
	
	public void clearRelationFragments() {
		relationFragments.clear();
	}
}
