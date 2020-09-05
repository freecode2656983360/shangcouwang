package com.zs.crowdfunding.mvc.handler;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zs.crowdfunding.entity.Menu;
import com.zs.crowdfunding.service.api.MenuService;
import com.zs.crowdfunding.util.ResultEntity;

@Controller
public class MenuHandler<V> {

	@Autowired
	private MenuService menuService;
	
	// 更新菜单选项
		@RequestMapping("/menu/update.json")
		@ResponseBody
		public ResultEntity<String> updateMenu(Menu menu) {

			menuService.updateMenu(menu);
			return ResultEntity.successWithoutDate();
		}

	// 删除菜单选项
	@RequestMapping("/menu/remove.json")
	@ResponseBody
	public ResultEntity<String> removeMenu(Integer id) {

		menuService.removeMenu(id);
		return ResultEntity.successWithoutDate();
	}

	// 添加菜单选项
	@RequestMapping("/menu/save.json")
	@ResponseBody
	public ResultEntity<String> addMenu(Menu menu) {

		menuService.saveMenu(menu);
		return ResultEntity.successWithoutDate();
	}

	// 查询所有菜单选项
	@RequestMapping("/menu/get/whole/tree.json")
	@ResponseBody
	public ResultEntity<Menu> getWholeTree() {

		List<Menu> menuList = menuService.getAll();

		HashMap<Integer, Menu> hashMap = new HashMap<Integer, Menu>();
		Menu root = null;

		for (Menu menu : menuList) {
			Integer id = menu.getId();
			hashMap.put(id, menu);
		}

		for (Menu menu : menuList) {
			Integer pid = menu.getPid();
			if (pid == null) {
				root = menu;
				continue;
			}
			Menu father = hashMap.get(pid);
			father.getChildren().add(menu);

		}

		return ResultEntity.successWithDate(root);
	}

}
