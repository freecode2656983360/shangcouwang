package com.zs.crowdfunding.service.api;

import java.util.List;

import com.zs.crowdfunding.entity.Menu;

public interface MenuService {


	List<Menu> getAll();
	
	void saveMenu(Menu menu);
	
	void removeMenu(Integer id);
	
	void updateMenu(Menu menu);

}
