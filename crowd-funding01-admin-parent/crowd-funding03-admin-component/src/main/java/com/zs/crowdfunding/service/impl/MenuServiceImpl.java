package com.zs.crowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zs.crowdfunding.entity.Menu;
import com.zs.crowdfunding.mapper.MenuMapper;
import com.zs.crowdfunding.service.api.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> getAll() {
		List<Menu> list = menuMapper.selectByExample(null);
		return list;
	}

	@Override
	public void saveMenu(Menu menu) {
		menuMapper.insert(menu);

	}

	@Override
	public void removeMenu(Integer id) {

		menuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateMenu(Menu menu) {
		menuMapper.updateByPrimaryKeySelective(menu);
		
	}

}
