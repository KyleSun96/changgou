package com.changgou.system.controller;

import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.system.service.AdminService;
import com.changgou.pojo.Admin;
import com.changgou.system.util.JwtUtil;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        List<Admin> adminList = adminService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", adminList);
    }

    /***
     * 根据ID查询数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        Admin admin = adminService.findById(id);
        return new Result(true, StatusCode.OK, "查询成功", admin);
    }


    /***
     * 新增数据
     * @param admin
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Admin admin) {
        adminService.add(admin);
        return new Result(true, StatusCode.OK, "添加成功");
    }


    /***
     * 修改数据
     * @param admin
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Admin admin, @PathVariable Integer id) {
        admin.setId(id);
        adminService.update(admin);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        adminService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }


    /**
     * @description: //TODO 管理员登录
     * @param: []
     * @return: com.changgou.entity.Result
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody Admin admin) {

        boolean login = adminService.login(admin);

        if (login) {
            // 密码正确，生成jwtToken给客户端

            // 新建一个map用于储存信息
            HashMap<String, String> info = new HashMap<>();
            String loginName = admin.getLoginName();

            // 工具类中已经设置 jwt 过期时间为1小时，如无特殊要求，传null即可
            String jwtToken = JwtUtil.createJWT(UUID.randomUUID().toString(), loginName, null);

            info.put("username", loginName);
            info.put("token", jwtToken);

            return new Result(true, StatusCode.OK, "登录成功", info);
        }
        return new Result(false, StatusCode.ERROR, "用户名或密码错误");
    }


    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search")
    public Result findList(@RequestParam Map searchMap) {
        List<Admin> list = adminService.findList(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }


    /***
     * 分页搜索实现
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result findPage(@RequestParam Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Admin> pageList = adminService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageList.getTotal(), pageList.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

}
