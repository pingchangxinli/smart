package com.lee.menu;

import com.lee.menu.domain.Menu;
import com.lee.menu.domain.SysMenu;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class MenuTreeUtil {
    /**
     * 创建用于用户展示的菜单
     * @param originMenus 原始菜单
     * @return 展示用菜单
     */
    public static List<Menu> buildMenuTree(List<SysMenu> originMenus) {
        //将原始菜单数据转换成 用于前端展示的菜单
        List<Menu> tmpMenus = convertToMenuList(originMenus);

        List<Menu> menuTree = new ArrayList<Menu>();
        for (Menu menu : tmpMenus) {
            log.debug("[Menu tree util],after convert: {}",menu);
            if (menu.getParentId().longValue() <= 0) {
                menu.setExact(null);
                Menu tempMenu = findChildren(tmpMenus, menu);
                menuTree.add(tempMenu);
            }
        }
        return menuTree;
    }
    private static Menu findChildren(List<Menu> menus,@NotNull Menu currentMenu) {
        Menu result = new Menu();
        for (Menu menu : menus) {
            if (currentMenu.getId() == menu.getParentId()) {
                if (CollectionUtils.isEmpty(currentMenu.getChildren())) {
                    currentMenu.setChildren(new ArrayList<>());
                }
                currentMenu.getChildren().add(findChildren(menus,menu));
            }
        }
        return currentMenu;
    }

    /**
     * 单个转换成用于用户展示的菜单
     * @param sysMenu 原始菜单
     * @return 展示用菜单
     */
    private static Menu convertSysToTree(SysMenu sysMenu) {
        Menu menu = new Menu();
        menu.setId(sysMenu.getId());
        menu.setParentId(sysMenu.getParentId());
        menu.setIcon(null);
        menu.setName(sysMenu.getName());
        menu.setPath(sysMenu.getPath());
        menu.setExact(true);
        return menu;
    }

    /**
     * 转换成用于用户展示的菜单
     * @param sysMenus 原始菜单
     * @return 展示用菜单
     */
    private static List<Menu> convertToMenuList(List<SysMenu> sysMenus) {
        List<Menu> menus = new ArrayList<>();
        sysMenus.stream().forEach(sysMenu -> {
            menus.add(convertSysToTree(sysMenu));
        });
        return menus;
    }
}
