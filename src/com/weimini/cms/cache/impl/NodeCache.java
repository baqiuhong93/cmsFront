package com.weimini.cms.cache.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weimini.cms.cache.INodeCache;
import com.weimini.cms.dao.IPublicDAO;
import com.weimini.cms.engity.NodeEntity;
import com.weimini.core.cache.MemcachedManager;
import com.weimini.core.util.CmsConstant;

@Component("nodeCache")
public class NodeCache extends MemcachedManager implements INodeCache  {
	
	@Autowired
	private IPublicDAO publicDAO;

	public List<NodeEntity> getNodesByNodeIds(String nodeIds) {
		List<NodeEntity> nodes = new ArrayList<NodeEntity>();
		String[] cates = nodeIds.split(",");
		
		for(String nodeId : cates) {
			String key = CmsConstant.MEMCACHE_NAMESPACE + "node_" + nodeId;
			NodeEntity node = (NodeEntity) client.get(key);
			if(node == null) {
				node = publicDAO.getNodeById(nodeId);
			} else {
				nodes.add(node);
				continue;
			}
			if(node != null) {
				client.set(key, 0, node);
				nodes.add(node);
			}
		}
		return nodes;
	}
	
	public List<NodeEntity> getNodesByUniqueCodes(String uniqueCodes) {
		List<NodeEntity> nodes = new ArrayList<NodeEntity>();
		String[] ucs = uniqueCodes.split(",");
		
		for(String uniqueCode : ucs) {
			String key = CmsConstant.MEMCACHE_NAMESPACE + "node_uc_" + uniqueCode;
			NodeEntity node = (NodeEntity) client.get(key);
			if(node == null) {
				node = publicDAO.getNodeByUniqueCode(uniqueCode);
			} else {
				nodes.add(node);
				continue;
			}
			if(node != null) {
				client.set(key, 0, node);
				nodes.add(node);
			}
		}
		return nodes;
	}

	@Override
	public List<Integer> getNodeSubtreeIds(NodeEntity nodeEntity) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_st_" + nodeEntity.getId();
		//client.delete(key);
		List<Integer> nodeSubtreeIds = (List<Integer>) client.get(key);
		if(nodeSubtreeIds == null) {
			nodeSubtreeIds = publicDAO.getNodeSubtreeIds(nodeEntity);
			if(nodeSubtreeIds != null) {
				client.set(key, 0, nodeSubtreeIds);
			}
		}
		return nodeSubtreeIds;
	}
	
	public void deleteNodeSubtreeIds(String nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_st_" + nodeId;
		client.delete(key);
	}

	public NodeEntity getNodeById(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_" + nodeId;
		NodeEntity node = (NodeEntity) client.get(key);
		if(node == null) {
			node = publicDAO.getNodeById(String.valueOf(nodeId));
			if(node != null) {
				client.set(key, 0, node);
			}
		}
		return node;
	}
	
	public List<NodeEntity> queryChildNodes(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "child_nodes_" + nodeId;
		List<Integer> nodeids = (List<Integer>) client.get(key);
		if(nodeids == null) {
			nodeids = publicDAO.queryChildNodes(nodeId);
			if(nodeids != null) {
				client.set(key, 0, nodeids);
			}
		}
		List<NodeEntity> nodeEntities = new ArrayList<NodeEntity>();
		if(nodeids != null) {
			for(int node_id : nodeids) {
				NodeEntity nodeEntity = getNodeById(node_id);
				nodeEntities.add(nodeEntity);
			}
		}
		return nodeEntities;
	}
	
	public void deleteChildNodes(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "child_nodes_" + nodeId;
		client.delete(key);
	}
	
	public void deleteNodeById(String nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_" + nodeId;
		client.delete(key);
	}

	@Override
	public int getNodeReleasedNum(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_released_num_" + nodeId;
		Integer releasedNum = (Integer) client.get(key);
		/**
		if(releasedNum == null) {
			releasedNum = 0;
			NodeEntity nodeEntity = this.getNodeById(nodeId);
			List<Integer> subTreeIds = publicDAO.getNodeSubtreeIds(nodeEntity);
			if(subTreeIds != null) {
				for(int cid : subTreeIds) {
					nodeEntity = this.getNodeById(cid);
					if(nodeEntity.isList()) {
						releasedNum = releasedNum + publicDAO.getNodeReleasedNum(nodeEntity.getId());
					}
				}
			}
			if(releasedNum != null) {
				client.set(key, 0, releasedNum);
			}
		}
		**/
		if(releasedNum == null) {
			releasedNum = publicDAO.getNodeReleasedNum(nodeId);
			client.set(key, 0, releasedNum);
		}
		return releasedNum == null ? 0 : releasedNum;
	}
	
	public void deleteNodeReleasedNum(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_released_num_" + nodeId;
		client.delete(key);
	}
	
	/**
	public int incrNodeReleasedNum(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_released_num_" + nodeId;
		
		Integer releasedNum = (Integer) client.get(key);
		if(releasedNum == null) {
			releasedNum = 0;
			NodeEntity nodeEntity = this.getNodeById(nodeId);
			List<Integer> subTreeIds = publicDAO.getNodeSubtreeIds(nodeEntity);
			if(subTreeIds != null) {
				for(int cid : subTreeIds) {
					nodeEntity = this.getNodeById(cid);
					if(nodeEntity.isList()) {
						releasedNum = releasedNum + publicDAO.getNodeReleasedNum(nodeEntity.getId());
					}
				}
			}
		}
		
		if(releasedNum != null) {
			releasedNum = releasedNum + 1;
			client.set(key, 0, releasedNum);
			//if(releasedNum % 10 == 0) {
				//publicDAO.updateNodeReleasedNum(nodeId, releasedNum);
			//}
		}
		return releasedNum == null ? 0 : releasedNum;
	}
	
	
	public int decrNodeReleasedNum(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_released_num_" + nodeId;
		
		Integer releasedNum = (Integer) client.get(key);
		if(releasedNum == null) {
			releasedNum = 0;
			NodeEntity nodeEntity = this.getNodeById(nodeId);
			List<Integer> subTreeIds = publicDAO.getNodeSubtreeIds(nodeEntity);
			if(subTreeIds != null) {
				for(int cid : subTreeIds) {
					nodeEntity = this.getNodeById(cid);
					if(nodeEntity.isList()) {
						releasedNum = releasedNum + publicDAO.getNodeReleasedNum(nodeEntity.getId());
					}
				}
			}
		}
		
		if(releasedNum != null) {
			releasedNum = releasedNum - 1;
			client.set(key, 0, releasedNum);
			//if(releasedNum % 10 == 0) {
			//	publicDAO.updateNodeReleasedNum(nodeId, releasedNum);
			//}
		}
		return releasedNum == null ? 0 : releasedNum;
	}
	**/

	/**
	public void initNodeReleasedNumAndClick() {
		List<Map<String,Object>> nodes = publicDAO.queryNodeReleasedNumAndClick();
		if(nodes != null) {
			for(Map<String,Object> node: nodes) {
				int nodeId = (Integer) node.get("id");
				int releasedNum = (Integer) node.get("released_num");
				int click = (Integer)node.get("click");
				String key = CmsConstant.MEMCACHE_NAMESPACE + "node_released_num_" + nodeId;
				client.set(key, 0, releasedNum);
				key = CmsConstant.MEMCACHE_NAMESPACE + "node_click_" + nodeId;
				client.set(key, 0, click);
			}
		}
	}
	**/
	
	@Override
	public int getNodeClick(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_click_" + nodeId;
		Integer click = (Integer) client.get(key);
		if(click == null) {
			NodeEntity node = publicDAO.getNodeById(String.valueOf(nodeId));
			if(node != null) {
				click = node.getClick();
				client.set(key, 0, click);
			}
		}
		return click == null ? 0 : click;
	}

	@Override
	public int incrNodeClick(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_click_" + nodeId;
		Integer click = (Integer) client.get(key);
		if(click == null) {
			click = publicDAO.getNodeClickById(nodeId);
		}
		
		if(click != null) {
			click = click +1;
			client.set(key, 0, click);
			if(click % 10 == 0) {
				publicDAO.updateNodeClick(nodeId, click);
			}
		}
		return click == null ? 0 : click;
	}

	@Override
	public void updateNodeStatus(int nodeId) {
		String key = CmsConstant.MEMCACHE_NAMESPACE + "node_" + nodeId;
		NodeEntity node = (NodeEntity) client.get(key);
		if(node != null) {
			node.setStatus(2);
			node.setReleased(true);
			client.set(key, 0, node);
		}
		publicDAO.updateNodeStatus(nodeId);
	}
}
