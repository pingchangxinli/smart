package com.lee.menu.domain;

import lombok.Data;

import java.util.List;
@Data
public class Menu {
    private Long id;
    private Long parentId;
    private String path;
    private String icon;
    private String name;
    private List<Menu> children;
    private Boolean exact;
}
