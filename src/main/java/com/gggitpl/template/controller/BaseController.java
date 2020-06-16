package com.gggitpl.template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gggitpl.template.controller.query.BaseQuery;
import com.gggitpl.template.model.BaseEntity;
import com.gggitpl.template.rest.RestResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author gggitpl
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@Validated
public class BaseController<E extends BaseEntity, D extends E, Q extends BaseQuery<E>> {

    @Autowired
    private IService<E> service;

    @PostMapping
    public ResponseEntity<E> create(@RequestBody @Valid D entity) {
        this.service.save(entity);
        return RestResp.createOk(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<E> delete(@PathVariable Long id) {
        this.service.removeById(id);
        return RestResp.deleteOk();
    }

    @PutMapping("/{id}")
    public ResponseEntity<E> update(@PathVariable Long id, @RequestBody @Valid D entity) {
        entity.setId(id);
        this.service.updateById(entity);
        return RestResp.updateOk(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<E> getById(@PathVariable Long id) {
        return RestResp.getOk(this.service.getById(id));
    }

    @GetMapping("/query")
    public ResponseEntity<Page<E>> query(Q query) {
        return RestResp.getOk(this.service.page(query.page(), query.queryWrapper()));
    }

}
