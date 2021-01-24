package edu.study.module.springbootvalidation.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zl
 * @date 2021/1/24 10:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class UserVO {

    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String REG_BANK = "^[1-9]\\d{9,29}$";
    public static final String REG_USERNAME = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$";
    public static final String REG_MOBILE = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
    public static final String REG_POSTAL_CODE = "^(0[1-7]|1[0-356]|2[0-7]|3[0-6]|4[0-7]|5[1-7]|6[1-7]|7[0-5]|8[013-6])\\d{4}$";

    @NotNull(message = "用户ID不能为空")
    private Integer id;
    /**
     * 用户名称
     */
    @NotBlank(message = "用户名不能为空")
    @Length(max = 20, message = "用户名不能超过20个字符")
    @Pattern(regexp = REG_USERNAME, message = "用户昵称限制：最多20字符，包含文字、字母和数字")
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 性别
     */
    private String sex;
    /**
     * 是否有效用户（true有效，false无效）
     */
    private Boolean enabled;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = REG_MOBILE, message = "手机号格式有误")
    private String mobile;
    /**
     * 用户年龄
     */
    @Nullable
    @Max(value = 150, message = "年龄最大值为150")
    @Min(value = 0, message = "年龄最小值为0")
    private Integer age;
    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = DATETIME)
    @JsonFormat(pattern = DATETIME, timezone = "GMT+8")
    @Past(message = "出生日期必须是过去时间")
    private Date birthday;
    /**
     * 归还时间
     */
    @DateTimeFormat(pattern = DATETIME)
    @JsonFormat(pattern = DATETIME, timezone = "GMT+8")
    @Future(message = "归还时间必须是将来时间")
    private Date callBackTime;
    /**
     * 身份证号
     */
    @NotNull
    @Length(min = 15, max = 18, message = "身份证号填写错误")
    private String idCard;
    /**
     * 银行账号
     */
    @Pattern(regexp = REG_BANK, message = "银行账号填写错误")
    private String bankId;
    /**
     * 成绩
     */
    @DecimalMax(value = "999.9", message = "成绩最高为999.9")
    @DecimalMin(value = "0", message = "成绩最小为0")
    private BigDecimal grade;
    /**
     * 邮政编码
     */
    @Pattern(regexp = REG_POSTAL_CODE, message = "非法邮政编码")
    private String postalCode;

    /**
     * 用户等级
     */
    @Range(min = 1L, max = 999L, message = "用户等级异常")
    private Integer level;
}
