import java.util.*;

/**
 * PO、VO、BO、DTO、DAO、POJO 区别测试代码
 * 
 * 测试内容：
 * 1. PO（Persistent Object）- 持久化对象
 * 2. VO（View Object）- 视图对象
 * 3. BO（Business Object）- 业务对象
 * 4. DTO（Data Transfer Object）- 数据传输对象
 * 5. DAO（Data Access Object）- 数据访问对象
 * 6. POJO（Plain Old Java Object）- 纯粹的 Java 对象
 * 7. 对象转换示例
 */
public class Question07_ObjectTypes {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("PO、VO、BO、DTO、DAO、POJO 区别测试代码");
        System.out.println("=".repeat(60));
        
        test01_PO();
        test02_VO();
        test03_BO();
        test04_DTO();
        test05_DAO();
        test06_POJO();
        test07_ObjectConversion();
        test08_RealWorldFlow();
        
        System.out.println("=".repeat(60));
        System.out.println("所有测试完成！");
        System.out.println("=".repeat(60));
    }
    
    /**
     * 测试1：PO（Persistent Object）- 持久化对象
     */
    public static void test01_PO() {
        System.out.println("\n--- 测试1：PO（Persistent Object） ---");
        System.out.println("定义：跟数据库表直接对应的实体类");
        System.out.println("特点：字段与数据库表列名一致，用于 ORM 框架");
        System.out.println();
        
        // 模拟 UserPO
        class UserPO {
            private Long id;
            private String name;
            private String phone;
            private Date createTime;
            
            public UserPO(Long id, String name, String phone) {
                this.id = id;
                this.name = name;
                this.phone = phone;
                this.createTime = new Date();
            }
            
            @Override
            public String toString() {
                return String.format("UserPO{id=%d, name='%s', phone='%s', createTime=%s}", 
                    id, name, phone, createTime);
            }
        }
        
        UserPO userPO = new UserPO(1L, "张三", "13800138000");
        System.out.println("示例：");
        System.out.println("  " + userPO);
        System.out.println();
        System.out.println("说明：PO 对应数据库表，字段一一对应");
        System.out.println("      不应该直接暴露给外部层");
    }
    
    /**
     * 测试2：VO（View Object）- 视图对象
     */
    public static void test02_VO() {
        System.out.println("\n--- 测试2：VO（View Object） ---");
        System.out.println("定义：给前端展示用的对象");
        System.out.println("特点：专为页面定制，可能聚合多个实体数据");
        System.out.println();
        
        // 模拟 UserVO
        class UserVO {
            private Long id;
            private String name;
            private String avatar;
            private String levelDesc;
            private String statusDesc;
            
            public UserVO(Long id, String name, String avatar) {
                this.id = id;
                this.name = name;
                this.avatar = avatar;
                this.levelDesc = "黄金会员";
                this.statusDesc = "正常";
            }
            
            @Override
            public String toString() {
                return String.format("UserVO{id=%d, name='%s', avatar='%s', levelDesc='%s', statusDesc='%s'}", 
                    id, name, avatar, levelDesc, statusDesc);
            }
        }
        
        UserVO userVO = new UserVO(1L, "张三", "https://example.com/avatar.jpg");
        System.out.println("示例：");
        System.out.println("  " + userVO);
        System.out.println();
        System.out.println("说明：VO 专为前端页面定制，包含计算字段和格式化字段");
        System.out.println("      如 levelDesc='黄金会员'、statusDesc='正常'");
    }
    
    /**
     * 测试3：BO（Business Object）- 业务对象
     */
    public static void test03_BO() {
        System.out.println("\n--- 测试3：BO（Business Object） ---");
        System.out.println("定义：承担业务逻辑的对象");
        System.out.println("特点：包含业务方法，在 Service 层内部流转");
        System.out.println();
        
        // 模拟 OrderPO
        class OrderPO {
            private Long id;
            private String status;
            private Date createTime;
            
            public OrderPO(Long id, String status) {
                this.id = id;
                this.status = status;
                this.createTime = new Date();
            }
        }
        
        // 模拟 OrderBO
        class OrderBO {
            private OrderPO order;
            private List<String> items;
            
            public OrderBO(OrderPO order, List<String> items) {
                this.order = order;
                this.items = items;
            }
            
            // 业务方法：判断是否超时
            public boolean isOverdue() {
                long diff = System.currentTimeMillis() - order.createTime.getTime();
                return diff > 30 * 60 * 1000;  // 30分钟
            }
            
            // 业务方法：判断是否可以取消
            public boolean canCancel() {
                return !isOverdue() && "PENDING".equals(order.status);
            }
            
            // 业务方法：获取状态描述
            public String getStatusDesc() {
                switch (order.status) {
                    case "PENDING": return "待付款";
                    case "PAID": return "已付款";
                    case "SHIPPED": return "已发货";
                    case "COMPLETED": return "已完成";
                    default: return "未知状态";
                }
            }
        }
        
        OrderPO orderPO = new OrderPO(1001L, "PENDING");
        OrderBO orderBO = new OrderBO(orderPO, Arrays.asList("商品A", "商品B"));
        
        System.out.println("示例：");
        System.out.println("  订单状态: " + orderBO.getStatusDesc());
        System.out.println("  是否超时: " + orderBO.isOverdue());
        System.out.println("  是否可以取消: " + orderBO.canCancel());
        System.out.println();
        System.out.println("说明：BO 包含业务逻辑方法，如 isOverdue()、canCancel()");
        System.out.println("      在 Service 层内部流转，不暴露给外部");
    }
    
    /**
     * 测试4：DTO（Data Transfer Object）- 数据传输对象
     */
    public static void test04_DTO() {
        System.out.println("\n--- 测试4：DTO（Data Transfer Object） ---");
        System.out.println("定义：用来传输数据的对象");
        System.out.println("特点：跨层传输，字段可能比 PO 少，减少网络传输");
        System.out.println();
        
        // 模拟 UserPO
        class UserPO {
            private Long id;
            private String name;
            private String phone;
            private String password;
            private Date createTime;
        }
        
        // 模拟 UserDTO
        class UserDTO {
            private Long id;
            private String name;
            private String phone;
            // 不包含 password、createTime 等敏感或不必要的字段
            
            @Override
            public String toString() {
                return String.format("UserDTO{id=%d, name='%s', phone='%s'}", id, name, phone);
            }
        }
        
        UserPO userPO = new UserPO();
        UserDTO userDTO = new UserDTO();
        // 模拟 PO 到 DTO 的转换
        System.out.println("示例：PO → DTO 转换");
        System.out.println("  UserPO: {id=1, name='张三', phone='138***', password='***', createTime=...}");
        System.out.println("  " + userDTO);
        System.out.println();
        System.out.println("说明：DTO 只包含需要的字段，减少网络传输量");
        System.out.println("      不包含 password、createTime 等敏感字段");
    }
    
    /**
     * 测试5：DAO（Data Access Object）- 数据访问对象
     */
    public static void test05_DAO() {
        System.out.println("\n--- 测试5：DAO（Data Access Object） ---");
        System.out.println("定义：专门负责操作数据库的接口或类");
        System.out.println("特点：定义数据库操作方法，不包含业务逻辑");
        System.out.println();
        
        // 模拟 UserPO
        class UserPO {
            private Long id;
            private String name;
            private String phone;
        }
        
        // 模拟 UserDao 接口
        interface UserDao {
            int insert(UserPO user);
            int update(UserPO user);
            int delete(Long id);
            UserPO findById(Long id);
            UserPO findByPhone(String phone);
        }
        
        // 模拟 UserDao 实现
        class UserDaoImpl implements UserDao {
            @Override
            public int insert(UserPO user) {
                System.out.println("    插入用户: " + user.name);
                return 1;  // 模拟返回影响行数
            }
            
            @Override
            public int update(UserPO user) {
                System.out.println("    更新用户: " + user.name);
                return 1;
            }
            
            @Override
            public int delete(Long id) {
                System.out.println("    删除用户ID: " + id);
                return 1;
            }
            
            @Override
            public UserPO findById(Long id) {
                System.out.println("    查询用户ID: " + id);
                return new UserPO();
            }
            
            @Override
            public UserPO findByPhone(String phone) {
                System.out.println("    查询手机号: " + phone);
                return new UserPO();
            }
        }
        
        UserDao userDao = new UserDaoImpl();
        
        System.out.println("示例：");
        System.out.println("  定义的方法：");
        System.out.println("    int insert(UserPO user)");
        System.out.println("    int update(UserPO user)");
        System.out.println("    int delete(Long id)");
        System.out.println("    UserPO findById(Long id)");
        System.out.println("    UserPO findByPhone(String phone)");
        System.out.println();
        System.out.println("  调用示例：");
        System.out.print("  插入用户: ");
        userDao.insert(new UserPO());
        System.out.println();
        System.out.println("说明：DAO 只负责数据库操作，不包含业务逻辑");
        System.out.println("      是接口定义规范，实现类由框架生成或手动实现");
    }
    
    /**
     * 测试6：POJO（Plain Old Java Object）- 纯粹的 Java 对象
     */
    public static void test06_POJO() {
        System.out.println("\n--- 测试6：POJO（Plain Old Java Object） ---");
        System.out.println("定义：最普通的 Java 对象，不依赖任何框架");
        System.out.println("特点：不继承特殊类、不实现框架接口、没有注解");
        System.out.println();
        
        // POJO 示例
        class User {
            private Long id;
            private String name;
            private Integer age;
            
            public User() {}
            
            public User(Long id, String name, Integer age) {
                this.id = id;
                this.name = name;
                this.age = age;
            }
            
            // getter/setter
            public Long getId() { return id; }
            public void setId(Long id) { this.id = id; }
            public String getName() { return name; }
            public void setName(String name) { this.name = name; }
            public Integer getAge() { return age; }
            public void setAge(Integer age) { this.age = age; }
            
            @Override
            public String toString() {
                return String.format("User{id=%d, name='%s', age=%d}", id, name, age);
            }
        }
        
        User user = new User(1L, "张三", 25);
        System.out.println("示例：");
        System.out.println("  " + user);
        System.out.println();
        System.out.println("说明：POJO 是纯粹的 Java 对象");
        System.out.println("      不继承 HttpServlet、不实现 Serializable");
        System.out.println("      不使用 @Entity、@Autowired 等注解");
        System.out.println("      PO、VO、BO、DTO、DAO 本质上都是 POJO");
    }
    
    /**
     * 测试7：对象转换
     */
    public static void test07_ObjectConversion() {
        System.out.println("\n--- 测试7：对象转换 ---");
        System.out.println("示例：PO → DTO → VO 的转换流程");
        System.out.println();
        
        // PO
        class UserPO {
            Long id = 1L;
            String name = "张三";
            String phone = "13800138000";
            String password = "encrypted_password";
            Date createTime = new Date();
        }
        
        // DTO
        class UserDTO {
            Long id;
            String name;
            String phone;
            
            @Override
            public String toString() {
                return String.format("UserDTO{id=%d, name='%s', phone='%s'}", id, name, phone);
            }
        }
        
        // VO
        class UserVO {
            Long id;
            String name;
            String avatar;
            String levelDesc;
            
            @Override
            public String toString() {
                return String.format("UserVO{id=%d, name='%s', avatar='%s', levelDesc='%s'}", 
                    id, name, avatar, levelDesc);
            }
        }
        
        UserPO userPO = new UserPO();
        
        // PO → DTO
        UserDTO userDTO = new UserDTO();
        userDTO.id = userPO.id;
        userDTO.name = userPO.name;
        userDTO.phone = userPO.phone;
        // 不包含 password、createTime
        
        // DTO → VO
        UserVO userVO = new UserVO();
        userVO.id = userDTO.id;
        userVO.name = userDTO.name;
        userVO.avatar = "https://example.com/avatar.jpg";
        userVO.levelDesc = "黄金会员";
        // 添加计算字段
        
        System.out.println("转换流程：");
        System.out.println("  1. UserPO: {id=1, name='张三', phone='138***', password='***', createTime=...}");
        System.out.println("  2. UserDTO: " + userDTO);
        System.out.println("  3. UserVO:  " + userVO);
        System.out.println();
        System.out.println("说明：");
        System.out.println("  PO → DTO: 只保留需要的字段，过滤敏感信息");
        System.out.println("  DTO → VO: 添加计算字段和格式化字段");
    }
    
    /**
     * 测试8：实际业务流程
     */
    public static void test08_RealWorldFlow() {
        System.out.println("\n--- 测试8：实际业务流程 ---");
        System.out.println("场景：用户查询订单详情");
        System.out.println();
        
        // 模拟各层对象
        class OrderPO {
            Long id = 1001L;
            String orderNo = "ORD20240101001";
            String status = "SHIPPED";
            Date createTime = new Date();
        }
        
        class OrderItemPO {
            Long id;
            String productName;
            String price;
            Integer quantity;
            
            OrderItemPO(Long id, String productName, String price, Integer quantity) {
                this.id = id;
                this.productName = productName;
                this.price = price;
                this.quantity = quantity;
            }
        }
        
        class OrderBO {
            OrderPO order;
            List<OrderItemPO> items;
            
            String getStatusDesc() {
                switch (order.status) {
                    case "PENDING": return "待付款";
                    case "PAID": return "已付款";
                    case "SHIPPED": return "已发货";
                    case "COMPLETED": return "已完成";
                    default: return "未知状态";
                }
            }
            
            boolean canRefund() {
                return "COMPLETED".equals(order.status) || "SHIPPED".equals(order.status);
            }
        }
        
        class OrderDetailVO {
            Long orderId;
            String orderNo;
            String statusDesc;
            List<String> products;
            boolean canRefund;
            
            @Override
            public String toString() {
                return String.format("OrderDetailVO{orderId=%d, orderNo='%s', statusDesc='%s', canRefund=%b}", 
                    orderId, orderNo, statusDesc, canRefund);
            }
        }
        
        // 模拟业务流程
        System.out.println("1. DAO 层：查询数据库");
        OrderPO orderPO = new OrderPO();
        List<OrderItemPO> items = Arrays.asList(
            new OrderItemPO(1L, "商品A", "99.00", 2),
            new OrderItemPO(2L, "商品B", "199.00", 1)
        );
        System.out.println("   查询到订单: " + orderPO.orderNo);
        System.out.println("   查询到订单项: " + items.size() + " 件");
        
        System.out.println("\n2. Service 层：业务逻辑处理");
        OrderBO orderBO = new OrderBO();
        orderBO.order = orderPO;
        orderBO.items = items;
        System.out.println("   订单状态: " + orderBO.getStatusDesc());
        System.out.println("   是否可以退款: " + orderBO.canRefund());
        
        System.out.println("\n3. Controller 层：组装 VO");
        OrderDetailVO vo = new OrderDetailVO();
        vo.orderId = orderBO.order.id;
        vo.orderNo = orderBO.order.orderNo;
        vo.statusDesc = orderBO.getStatusDesc();
        vo.products = items.stream()
            .map(item -> item.productName)
            .toList();
        vo.canRefund = orderBO.canRefund();
        System.out.println("   " + vo);
        
        System.out.println("\n说明：");
        System.out.println("  DAO 层：查询数据库，返回 PO");
        System.out.println("  Service 层：处理业务逻辑，使用 BO");
        System.out.println("  Controller 层：组装返回结果，返回 VO");
    }
}
