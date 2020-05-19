package indi.tudan.learn.cloud.cache.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import indi.tudan.learn.cloud.cache.service.RedisService;
import indi.tudan.learn.cloud.utils.spring.ResponseUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 缓存服务
 *
 * @author wangtan
 * @date 2020-05-15 11:38:52
 * @since 1.0
 */
@Api(value = "RedisController", description = "缓存服务接口")
@RestController
@RequestMapping("/api")
@SuppressWarnings("rawtypes")
public class RedisController {

    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisService redisService;

    /**
     * 字符串操作：获取某 key 的 value
     *
     * @param key key
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 15:14:10
     */
    @ApiOperation(value = "字符串操作：获取某 key 的 value", notes = "获取某 key 的 value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string")
    })
    @GetMapping("/get")
    public ResponseEntity get(@RequestParam String key) {
        logger.debug("enter method: RedisController.get");
        String result;
        try {
            result = redisService.get(key);
        } catch (Exception e) {
            String msg = "获取 key: " + key + " 的值失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        logger.debug("RedisController.get return data: {}", result);
        return ResponseUtils.build(result);
    }

    /**
     * 字符串操作：设置 key, value, expire
     *
     * @param key    key
     * @param value  value
     * @param expire 过期时间，可为空
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 15:15:22
     */
    @ApiOperation(value = "字符串操作：设置 key, value, expire", notes = "设置 key, value, expire（过期时间）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "expire", value = "过期时间", required = false, dataType = "integer")
    })
    @PostMapping("/put")
    public ResponseEntity put(@RequestParam String key,
                              @RequestParam String value,
                              @RequestParam(required = false) Integer expire) {
        logger.debug("enter method: RedisController.put");
        String msg = "存储 key: " + key;
        try {
            if (ObjectUtil.isNotNull(expire) && NumberUtil.isValidNumber(expire)) {
                redisService.put(key, value, expire);
            } else {
                redisService.put(key, value);
            }
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(true, null, msg + " 成功。", null, HttpStatus.OK);
    }

    /**
     * 当 key 不存在时，设置 key, value
     *
     * @param key   key
     * @param value value
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 16:26:33
     */
    @ApiOperation(value = "当 key 不存在时，设置 key, value", notes = "当 key 不存在时，设置 key, value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "string")
    })
    @PostMapping("/putIfNotExists")
    public ResponseEntity putIfNotExists(@RequestParam String key,
                                         @RequestParam String value) {
        logger.debug("enter method: RedisController.putIfNotExists");
        boolean result;
        String msg = "存储 key: " + key;
        try {
            result = redisService.putIfNotExists(key, value);
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(result, null,
                msg + (result ? " 成功。" : " 失败，该键已经存在。"), null, HttpStatus.OK);
    }

    /**
     * Json 操作：获取某 key 的 value
     *
     * @param key key
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-18 09:35:51
     */
    @ApiOperation(value = "Json 操作：获取某 key 的 value", notes = "获取某 key 的 value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string")
    })
    @GetMapping("/getJson")
    public ResponseEntity getJson(@RequestParam String key) {
        logger.debug("enter method: RedisController.getJson");
        JSONObject result;
        try {
            result = redisService.get(key, JSONObject.class);
        } catch (Exception e) {
            String msg = "获取 key: " + key + " 的值失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        logger.debug("RedisController.getJson return data: {}", result);
        return ResponseUtils.build(result);
    }

    /**
     * Json 操作：设置 key, value, expire
     *
     * @param key    key
     * @param value  value（json 字符串）
     * @param expire 过期时间，可为空
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-17 15:17:58
     */
    @ApiOperation(value = "Json 操作：设置 key, value, expire", notes = "设置 key, value, expire")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "expire", value = "过期时间", required = false, dataType = "integer")
    })
    @PostMapping("/putJson")
    public ResponseEntity putJson(@RequestParam String key,
                                  @RequestParam String value,
                                  @RequestParam(required = false) Integer expire) {
        logger.debug("enter method: RedisController.putJson");
        String msg = "存储 key: " + key;
        try {
            if (ObjectUtil.isNotNull(expire) && NumberUtil.isValidNumber(expire)) {
                redisService.put(key, JSON.parseObject(value), expire);
            } else {
                redisService.put(key, JSON.parseObject(value));
            }
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(true, null, msg + " 成功。", null, HttpStatus.OK);
    }

    /**
     * Json 操作：当 key 不存在时，设置 key, value
     *
     * @param key   key
     * @param value value（json 字符串）
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 16:26:33
     */
    @ApiOperation(value = "Json 操作：当 key 不存在时，设置 key, value", notes = "当 key 不存在时，设置 key, value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "string")
    })
    @PostMapping("/putJsonIfNotExists")
    public ResponseEntity putJsonIfNotExists(@RequestParam String key,
                                             @RequestParam String value) {
        logger.debug("enter method: RedisController.putJsonIfNotExists");
        boolean result;
        String msg = "存储 key: " + key;
        try {
            result = redisService.putIfNotExists(key, JSON.parseObject(value));
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(result, null,
                msg + (result ? " 成功。" : " 失败，该键已经存在。"), null, HttpStatus.OK);
    }

    /**
     * 字符串操作：获取某 dir/key 的 value
     *
     * @param dir dir
     * @param key key
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-18 11:25:51
     */
    @ApiOperation(value = "字符串操作：获取某 dir/key 的 value", notes = "获取某 dir/key 的 value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "目录", required = true, dataType = "string"),
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string")
    })
    @GetMapping("/getDir")
    public ResponseEntity getDir(@RequestParam String dir,
                                 @RequestParam String key) {
        logger.debug("enter method: RedisController.getDir");
        String result;
        try {
            result = redisService.getDir(dir, key);
        } catch (Exception e) {
            String msg = "获取目录 dir: " + dir + ", key: " + key + " 的值失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        logger.debug("RedisController.getDir return data: {}", result);
        return ResponseUtils.build(result);
    }

    /**
     * 字符串操作：设置 dir/key, value, expire
     *
     * @param dir    dir
     * @param key    key
     * @param value  value
     * @param expire 过期时间，可为空
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-18 11:25:55
     */
    @ApiOperation(value = "字符串操作：设置 dir/key, value, expire", notes = "设置 dir/key, value, expire")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "目录", required = true, dataType = "string"),
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "expire", value = "过期时间", required = false, dataType = "integer")
    })
    @PostMapping("/putDir")
    public ResponseEntity putDir(@RequestParam String dir,
                                 @RequestParam String key,
                                 @RequestParam String value,
                                 @RequestParam(required = false) Integer expire) {
        logger.debug("enter method: RedisController.putDir");
        String msg = "存储目录 dir: " + dir + ", key: " + key;
        try {
            if (ObjectUtil.isNotNull(expire) && NumberUtil.isValidNumber(expire)) {
                redisService.putDir(dir, key, value, expire);
            } else {
                redisService.putDir(dir, key, value);
            }
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(true, null, msg + " 成功。", null, HttpStatus.OK);
    }

    /**
     * Json 操作：获取某 dir/key 的 value
     *
     * @param dir dir
     * @param key key
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-18 11:35:54
     */
    @ApiOperation(value = "Json 操作：获取某 dir/key 的 value", notes = "获取某 dir/key 的 value")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "目录", required = true, dataType = "string"),
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string")
    })
    @GetMapping("/getDirJson")
    public ResponseEntity getDirJson(@RequestParam String dir,
                                     @RequestParam String key) {
        logger.debug("enter method: RedisController.getDirJson");
        JSONObject result;
        try {
            result = redisService.getDir(dir, key, JSONObject.class);
        } catch (Exception e) {
            String msg = "获取 dir: " + dir + ", key: " + key + " 的值失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        logger.debug("RedisController.getDirJson return data: {}", result);
        return ResponseUtils.build(result);
    }

    /**
     * Json 操作：设置 dir/key, value, expire
     *
     * @param dir    dir
     * @param key    key
     * @param value  value（json 字符串）
     * @param expire 过期时间，可为空
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-18 11:33:24
     */
    @ApiOperation(value = "Json 操作：设置 dir/key, value, expire", notes = "设置 dir/key, value, expire")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "目录", required = true, dataType = "string"),
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "string"),
            @ApiImplicitParam(name = "expire", value = "过期时间", required = false, dataType = "integer")
    })
    @PostMapping("/putDirJson")
    public ResponseEntity putDirJson(@RequestParam String dir,
                                     @RequestParam String key,
                                     @RequestParam String value,
                                     @RequestParam(required = false) Integer expire) {
        logger.debug("enter method: RedisController.putDirJson");
        String msg = "存储目录 dir: " + dir + ", key: " + key;
        try {
            if (ObjectUtil.isNotNull(expire) && NumberUtil.isValidNumber(expire)) {
                redisService.putDir(dir, key, JSON.parseObject(value), expire);
            } else {
                redisService.putDir(dir, key, JSON.parseObject(value));
            }
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(true, null, msg + " 成功。", null, HttpStatus.OK);
    }

    /**
     * 获取某 dir 下所有的 key
     *
     * @param dir            dir
     * @param excludeSubDirs 要排除的子目录列表，即这里列出的子目录下的key将不予列出（key 以英文逗号隔开，即：key1,key2,key3,...）
     * @param keyWord        关键词
     * @param offset         偏移量，用于分页查询
     * @param limit          每次查询返回条数，用于分页查询，如果limit=-1，则返回全部
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-18 15:22:29
     */
    // FIXIT: 暂不支持
    @ApiOperation(value = "获取某 dir 下所有的 key（暂不支持）", notes = "获取某 dir 下所有的 key")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "目录", required = true, dataType = "string"),
            @ApiImplicitParam(name = "excludeSubDirs", value = "要排除的子目录列表，即这里列出的子目录下的key将不予列出（key 以英文逗号隔开，即：key1,key2,key3,...）", required = true, dataType = "string"),
            @ApiImplicitParam(name = "keyWord", value = "关键词", required = true, dataType = "string"),
            @ApiImplicitParam(name = "offset", value = "偏移量，用于分页查询", required = true, dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每次查询返回条数，用于分页查询，如果limit=-1，则返回全部", required = true, dataType = "int")
    })
    @GetMapping("/listKeys")
    public ResponseEntity listKeys(@RequestParam String dir,
                                   @RequestParam String excludeSubDirs,
                                   @RequestParam String keyWord,
                                   @RequestParam int offset,
                                   @RequestParam int limit) {
        logger.debug("enter method: RedisController.listKeys");
        List<String> result;
        try {
            result = redisService.listKeys(dir,
                    StrUtil.split(excludeSubDirs, ",", 0, true, true),
                    keyWord,
                    offset,
                    limit);
        } catch (Exception e) {
            String msg = StrUtil.format("获取所有的 key 失败。 dir: {}, 排除 excludeSubDirs: {}, 关键字 keyWord: {}, 偏移量 offset: {}, 返回条数 limit {}。",
                    dir, excludeSubDirs, keyWord, offset, limit);
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        logger.debug("RedisController.listKeys return data: {}", result);
        return ResponseUtils.build(result);
    }

    /**
     * 移除某 key
     *
     * @param key key
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 16:38:32
     */
    @ApiOperation(value = "移除某 key", notes = "移除某 key")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string")
    })
    @DeleteMapping("/remove")
    public ResponseEntity remove(@RequestParam String key) {
        logger.debug("enter method: RedisController.remove");
        boolean result;
        String msg = "移除 key: " + key;
        try {
            result = redisService.remove(key);
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(result, null,
                msg + (result ? " 成功。" : " 失败，该键不存在。"), null, HttpStatus.OK);
    }

    /**
     * 移除某 dir 下指定的 keyList
     *
     * @param dir     dir
     * @param keyList keyList（key 以英文逗号隔开，即：key1,key2,key3,...）
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-18 13:45:33
     */
    @ApiOperation(value = "移除某 dir 下指定的 keyList", notes = "移除某 dir 下指定的 keyList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dir", value = "目录", required = true, dataType = "string"),
            @ApiImplicitParam(name = "keyList", value = "key 以英文逗号隔开，即：key1,key2,key3,...", required = true, dataType = "string")
    })
    @DeleteMapping("/batchRemove")
    public ResponseEntity batchRemove(@RequestParam String dir,
                                      @RequestParam String keyList) {
        logger.debug("enter method: RedisController.remove");
        String msg = "移除 dir: " + dir + ", keyList: " + keyList;
        try {
            redisService.batchRemove(dir, StrUtil.split(keyList, ",", 0, true, true));
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(true, null, msg + " 成功。", null, HttpStatus.OK);
    }

    /**
     * 移除所有 key
     *
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 16:45:15
     */
    @ApiOperation(value = "移除所有 key", notes = "移除所有 key")
    @DeleteMapping("/removeAll")
    public ResponseEntity removeAll() {
        logger.debug("enter method: RedisController.removeAll");
        String msg = "移除所有 key";
        try {
            redisService.removeAll();
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(true, null, msg + " 成功。", null, HttpStatus.OK);
    }

    /**
     * 重设 key 的过期时间
     *
     * @param key    key
     * @param expire 过期时间，可为空
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 16:15:22
     */
    @ApiOperation(value = "重设 key 的过期时间", notes = "重设 key 的过期时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string"),
            @ApiImplicitParam(name = "expire", value = "过期时间", required = true, dataType = "integer")
    })
    @PostMapping("/expire")
    public ResponseEntity expire(@RequestParam String key,
                                 @RequestParam Integer expire) {
        logger.debug("enter method: RedisController.expire");
        String msg = "重设过期时间 key: " + key;
        try {
            redisService.expire(key, expire);
        } catch (Exception e) {
            msg = msg + " 失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        return ResponseUtils.build(true, null, msg + " 成功。", null, HttpStatus.OK);
    }

    /**
     * 获取某 key 的过期时间
     *
     * @param key key
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 17:02:12
     */
    @ApiOperation(value = "获取某 key 的过期时间", notes = "获取某 key 的过期时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string")
    })
    @GetMapping("/getExpireTime")
    public ResponseEntity getExpireTime(@RequestParam String key) {
        logger.debug("enter method: RedisController.getExpireTime");
        int result;
        try {
            result = redisService.getExpireTime(key);
        } catch (Exception e) {
            String msg = "获取 key: " + key + " 的过期时间失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, null, msg, null, HttpStatus.OK);
        }
        logger.debug("RedisController.getExpireTime return data: {}", result);
        return ResponseUtils.build(result);
    }

    /**
     * 计数器增加 1 或指定步长，如果 key 不存在，则自动增加一个计数器并将计数器设置为 1 或指定步长
     *
     * @param key   key
     * @param step 步长
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 17:07:35
     */
    @ApiOperation(value = "计数器增加 1 或指定步长，如果 key 不存在，则自动增加一个计数器并将计数器设置为 1 或指定步长", notes = "计数器增加 1 或指定步长，如果 key 不存在，则自动增加一个计数器并将计数器设置为 1 或指定步长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string"),
            @ApiImplicitParam(name = "step", value = "步长", required = true, dataType = "long")
    })
    @GetMapping("/incr")
    public ResponseEntity incr(@RequestParam String key,
                               @RequestParam(required = false) Long step) {
        logger.debug("enter method: RedisController.incr");
        long result = 0L;
        try {
            if (ObjectUtil.isNotNull(step) && NumberUtil.isValidNumber(step)) {
                result = redisService.incr(key, step);
            } else {
                result = redisService.incr(key);
            }
        } catch (Exception e) {
            String msg = "增长 key: " + key + " 的序列失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, result, msg, null, HttpStatus.OK);
        }
        logger.debug("RedisController.incr return data: {}", result);
        return ResponseUtils.build(result);
    }

    /**
     * 计数器增加 1 或指定步长，如果 key 不存在，则自动增加一个计数器并将计数器设置为 1 或指定步长
     *
     * @param key   key
     * @param step 步长
     * @return ResponseEntity
     * @author wangtan
     * @date 2020-05-15 17:14:59
     */
    @ApiOperation(value = "计数器 -1 或减少指定步长，如果 key 不存在，则自动增加一个计数器，并设置值 -1 或 负指定步长", notes = "计数器增加 1 或指定步长，如果 key 不存在，则自动增加一个计数器并将计数器设置为 1 或指定步长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "string"),
            @ApiImplicitParam(name = "step", value = "步长", required = true, dataType = "long")
    })
    @GetMapping("/decr")
    public ResponseEntity decr(@RequestParam String key,
                               @RequestParam(required = false) Long step) {
        logger.debug("enter method: RedisController.decr");
        long result = 0L;
        try {
            if (ObjectUtil.isNotNull(step) && NumberUtil.isValidNumber(step)) {
                result = redisService.decr(key, step);
            } else {
                result = redisService.decr(key);
            }
        } catch (Exception e) {
            String msg = "减少 key: " + key + " 的序列失败。";
            logger.error(msg, e);
            return ResponseUtils.build(false, result, msg, null, HttpStatus.OK);
        }
        logger.debug("RedisController.decr return data: {}", result);
        return ResponseUtils.build(result);
    }

}
