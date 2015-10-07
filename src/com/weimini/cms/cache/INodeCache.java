package com.weimini.cms.cache;

import java.util.List;

import com.weimini.cms.engity.NodeEntity;


public interface INodeCache {
	
	public List<NodeEntity> getNodesByNodeIds(String nodeIds);
	
	public List<NodeEntity> getNodesByUniqueCodes(String uniqueCodes);
	
	public List<Integer> getNodeSubtreeIds(NodeEntity nodeEntity);
	
	public void deleteNodeSubtreeIds(String nodeId);
	
	public List<NodeEntity> queryChildNodes(int nodeId);
	
	public void deleteChildNodes(int nodeId);
	
	public NodeEntity getNodeById(int nodeId);
	
	public void deleteNodeById(String nodeId);
	
	public int incrNodeClick(int nodeId);
	
	public void updateNodeStatus(int nodeId);
	
	//public int incrNodeReleasedNum(int nodeId);
	
	//public int decrNodeReleasedNum(int nodeId);
	
	public int getNodeReleasedNum(int nodeId);
	
	public void deleteNodeReleasedNum(int nodeId);
	
	public int getNodeClick(int nodeId);
	
	//public void initNodeReleasedNumAndClick();

}
