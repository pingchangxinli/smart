package com.lee.menu;

import com.lee.menu.domain.SysMenuResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class MenuTreeUtil {
    /**
     * 创建用于用户展示的菜单
     *
     * @param originMenus 原始菜单
     * @return 展示用菜单
     */
    public static List<SysMenuResponse> buildMenuTree(List<SysMenuResponse> originMenus) {
        List<SysMenuResponse> menuTree = new ArrayList<SysMenuResponse>();
        for (SysMenuResponse menu : originMenus) {
            log.debug("[Menu tree util],after convert: {}", menu);
            if (menu.getParentId().longValue() <= 0) {
                menu.setExact(null);
                SysMenuResponse tempMenu = findChildren(originMenus, menu);
                menuTree.add(tempMenu);
            }
        }
        return menuTree;
    }

    /**
     * 查询 menus中 currentMenu 下的所有子菜单
     *
     * @param menus       所有菜单
     * @param currentMenu 当前菜单
     * @return 当前菜单下的所有子菜单
     */
    private static SysMenuResponse findChildren(List<SysMenuResponse> menus, @NotNull SysMenuResponse currentMenu) {
        for (SysMenuResponse menu : menus) {
            log.debug("MenuTreeUtil findChildren,before query,parent menu id:{},children menu id:{}", currentMenu.getId(),
                    menu.getId());
            if (currentMenu.getId().equals(menu.getParentId())) {
                log.debug("MenuTreeUtil findChildren,parent menu id:{},children menu id:{}", currentMenu.getId(),
                        menu.getId());
                if (CollectionUtils.isEmpty(currentMenu.getChildren())) {
                    currentMenu.setChildren(new ArrayList<>());
                }
                currentMenu.getChildren().add(findChildren(menus, menu));
            }
        }
        return currentMenu;
    }

    /**
     * 单个转换成用于用户展示的菜单
     *
     * @param sysMenu 原始菜单
     * @return 展示用菜单
     */
    private static SysMenuResponse convertSysToTree(SysMenuResponse sysMenu) {
        SysMenuResponse menu = new SysMenuResponse();
        menu.setId(sysMenu.getId());
        menu.setParentId(sysMenu.getParentId());
        menu.setIcon(null);
        menu.setName(sysMenu.getName());
        menu.setPath(sysMenu.getPath());
        menu.setExact(true);
        return menu;
    }


}
