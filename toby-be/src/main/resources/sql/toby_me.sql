/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : toby_me

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 14/08/2023 00:50:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for articles
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `slug` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author_id` bigint NOT NULL,
  `is_approved` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKe02fs2ut6qqoabfhj325wcjul`(`author_id` ASC) USING BTREE,
  CONSTRAINT `FKe02fs2ut6qqoabfhj325wcjul` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of articles
-- ----------------------------
INSERT INTO `articles` VALUES (1, '2023-08-13 22:18:35', '2023-08-13 22:26:54', '<h2><strong>Javascript</strong></h2><p><strong>1. This là gì ?</strong></p><p><strong>2. Có những loại biến nào, sự khác nhau của chúng</strong></p><p>Var, Let: có thể reassigned</p><p>Const: không thể reassigned</p><p><strong>3. Trình bày các loại scope</strong></p><p>Global: Bao gồm biến var, const, let được khai báo ở ngoài cùng file javascript.</p><p>Block code: Bao gồm biến let, const được khai báo trong block code như: if else, switch case sẽ có phạm vi block code, trường hợp var được khai báo trong block code sẽ có phạm vi global.</p><p>Local: Còn được gọi là phạm vi hàm, bao gồm let, const, var hoặc hàm được khai báo trong một hàm sẽ tạo ra phạm vi hàm</p><p><strong>4. Khái niệm Closure</strong></p><p><strong>5. Trình bày sự khác nhau giữa local stogare, cookies, session storage ?</strong></p><p><strong>6. Xử lý bất đồng bộ ( Cách hoạt động, cú pháp của Promise, Callback, Async await )</strong></p><p><strong>7. Khái niệm Hoisting</strong></p><p>Di chuyển phần khai báo biến lên đầu</p><p><strong>8. Những thay đổi ở ES6</strong></p><p><strong>9. Truthy, Falsy</strong></p><p><strong>10. Bất đồng bộ và đồng bộ trong Javascript là gì ? Javascript là ngôn ngữ lập trình đồng bộ hay bất đồng bộ ?</strong></p><p><strong>11. Tham trị, tham chiếu</strong></p><p><br></p><h2><strong>ReactJS</strong></h2><p><br></p><p><strong>1. ReactJS là gì? Tại sao sử dụng ReactJS</strong></p><p><strong>2. Trình bày life cycle của ReactJS</strong></p><p><strong>3. useEffect có thể dùng trong các giai đoạn nào của life cycle?</strong></p><p><strong>4. Virtual DOM</strong></p><p>Trong React, mỗi phần giao diện người dùng là một Component. Khi trạng thái của một Component thay đổi, React sẽ cập nhật DOM ảo. Khi DOM ảo đã được cập nhật, React sau đó sẽ so sánh phiên bản hiện tại của DOM ảo với phiên bản trước của DOM ảo. Quá trình này được gọi là \"diffing\".</p><p>Khi React biết đối tượng DOM ảo nào đã thay đổi, thì React chỉ cập nhật các đối tượng đó trong DOM thực. Điều này làm cho hiệu suất tốt hơn nhiều so với thao tác trực tiếp với DOM thực. Do đó làm cho React nổi bật như một thư viện JavaScript hiệu suất cao.</p><p><strong>5. Key là gì?</strong></p><p>Key sẽ được dùng khi sử dụng hàm&nbsp;<strong>map</strong>&nbsp;để render một danh sách, key có tác dụng giúp cho React so sánh giữa trạng thái cũ và mới của các thành phần trong danh sách để đưa ra quyết định thành phần nào được re-render.</p><p><strong>6. Trình bày một số cách để tối ưu performance</strong></p><p>Để tối ưu performance cần tránh những lần re-render không cần thiết, có một số cách để tránh re-render như sau:</p><ul><li>Khi sử dụng useEffect cần Kiểm tra chặt chẽ dependency, sử dụng eventListenner cần phải có clean up.</li><li>Sử dụng useMemo và useCallback để hạn chế khởi tạo những tác vụ phức tạp.</li><li>Sử dụng memo để hạn chế re-render không cần thiết</li><li>Sử dụng key khi render list</li><li>Sử dụng useRef để lưu value trong một vài trường hợp không cần re-render</li></ul><p><strong>7. Có thể gán trực tiếp state mà không thông qua hàm setState được không?</strong></p><p>Có thể gán trực tiếp state bằng giá trị mới, nhưng component chỉ re-render khi thay đổi state thông qua hàm setState</p><p><strong>9. Phân biệt state và props</strong></p><p>State: quản lý các trạng thái của component</p><p>Props: được dùng để chia sẽ các trạng thái của component này cho các component khác</p><p><strong>10. Khi nào component re-render</strong></p><p>Khi các state của component thay đổi</p><p>Khi component cha re-render</p><p><strong>11. Cách ngăn component re-render khi không có sự thay đổi</strong></p><p>Sử dụng memo</p><p><strong>12. Trình bày cách thức hoạt động của useEffect?</strong></p><p><strong>13. Custom hook là gì ?</strong></p><p><strong>14. Phân biệt useCallback và useMemo và memo</strong></p><p><strong>15. Có nên sử dụng useCallback và useMemo trong mọi trường hợp hay không</strong></p><p><strong>16. Tại sao cần state management</strong></p><p><strong>17. Phân biệt Context API so với Redux</strong></p><p><strong>18. Code splitting</strong></p><p>Là kĩ thuật chia nhỏ file js, giúp tăng hiệu suất tải trang bằng cách sử dụng lazy loading. Bạn có thể xem thêm tại&nbsp;<a href=\"https://react.dev/reference/react/lazy\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(43, 109, 173);\">https://react.dev/reference/react/lazy</a></p><p><strong>19. React là CSR hay SSR</strong></p><p><strong>20. Trình bày một số design pattern trong React</strong></p>', 'Bài viết này mình sẽ liệt kê một vài câu hỏi thường xuyên được hỏi khi phỏng vấn ReactJS.', 'Bộ-câu-hỏi-phỏng-vấn-ReactJS-từ-cơ-bản-đến-nâng-cao-2023-08-13T22:25:00.541508', 'Bộ câu hỏi phỏng vấn ReactJS từ cơ bản đến nâng cao', 2, 1);
INSERT INTO `articles` VALUES (2, '2023-08-13 22:21:24', '2023-08-13 22:26:52', '<h3><strong>1. JSX</strong></h3><p>JSX là viết tắt của từ Javascript syntax extention (phần bổ sung cú pháp của javascript). Là một sự hòa trộn của Javascript và XML. Là một dạng ngôn ngữ cho phép viết các mã HTML trong Javascript. Giúp ta viết mã HTML đơn giản hơn trong javascript.</p><p>JSX = JS + XML</p><p>Ưu điểm của nó bao gồm:</p><ul><li>Nhanh hơn: JSX thực hiện tối ưu hóa trong khi biên dịch sang mã Javacsript. Các mã này cho thời gian thực hiện nhanh hơn nhiều so với một mã tương đương viết trực tiếp bằng Javascript.</li><li>An toàn hơn: JSX là kiểu statically-typed , nghĩa là nó được biên dịch trước khi chạy. Vì vậy khi có lỗi nó sẽ báo ngay trong lúc biên dịch.</li><li>Dễ dàng sử dụng.</li></ul><p><br></p><p>Ví dụ:</p><p>HTML thông thường:</p><pre class=\"ql-syntax\" spellcheck=\"false\">&lt;div class=\"card\"&gt;\n    &lt;img class=\"card-img-top\" src=\"\" alt=\"Card image cap\"&gt;\n    &lt;div class=\"card-body\"&gt;\n        &lt;h5 class=\"card-title\"&gt;Hello World!&lt;/h5&gt;\n        &lt;p class=\"card-text\"&gt;Some quick example text to build on the card title and make up the bulk of the card\'s content.&lt;/p&gt;\n        &lt;a href=\"#\" class=\"btn btn-primary\"&gt;Go somewhere&lt;/a&gt;\n    &lt;/div&gt;\n&lt;/div&gt;\n</pre><p>Chuyển sang JSX:</p><pre class=\"ql-syntax\" spellcheck=\"false\">&lt;div className=\"card\"&gt;\n    &lt;img className=\"card-img-top\" src alt=\"Card image cap\" /&gt;\n    &lt;div className=\"card-body\"&gt;\n        &lt;h5 className=\"card-title\"&gt;Hello World!&lt;/h5&gt;\n        &lt;p className=\"card-text\"&gt;Some quick example text to build on the card title and make up the bulk of the card\'s content.&lt;/p&gt;\n        &lt;a href=\"#\" className=\"btn btn-primary\"&gt;Go somewhere&lt;/a&gt;\n    &lt;/div&gt;\n&lt;/div&gt;\n</pre><p>Về cơ bản thì nó cũng không có gì thay đổi nhiều.&nbsp;</p><p><br></p><h3><strong>2. Component</strong></h3><p>Một trong những khái niệm quan trọng trước khi học reactjs đó là component.</p><p>Component một khối đóng gói, bên trong gồm các thẻ html, props, state ... hiểu đơn giản là một thẻ html mình tự định nghĩa ra đế đóng gói một component lại. Mỗi component này sẽ có mỗi chức năng, mục đích riêng biệt để ta có thể quản lý cũng như xử lý dữ liệu dễ dàng hơn.</p><p>Đây là 1 nền tảng của reactjs, Với một trang web thông thường có chung một trang lớn, một trang lớn có nhiều component, điều này làm ta sẽ dễ bị nhầm lẫn giữa các component, ko thể hiện rõ được chức năng của từng component. Vì vậy bạn nên chuyển đổi html thông thường sang dạng component. Một trang web được chia nhỏ ra làm nhiều component để ta dễ quản lý và xử lý dữ liệu cho mỗi component. Nếu ta ko chia nhỏ ra các component thì việc xử lý dữ liệu sẽ khá phức tạp và rắc rối. Vì component đc viết bằng js nên bạn có thể dễ dang truyền dữ liệu cũng như xử lý dữ liệu ở đây.<em>Ví dụ:</em></p><p>Ta có 1 component với chức năng làm menu cho trang web của chúng ta.</p><pre class=\"ql-syntax\" spellcheck=\"false\">class Nav extends Component {\n   render() {\n      return (\n         &lt;nav className=\"navbar navbar-expand-lg navbar-light bg-light\"&gt;\n            &lt;div className=\"collapse navbar-collapse\" id=\"navbarNav\"&gt;\n                &lt;ul className=\"navbar-nav\"&gt;\n                    &lt;li className=\"nav-item active\"&gt;\n                        &lt;a className=\"nav-link\" href=\"#\"&gt;Home&lt;/a&gt;\n                    &lt;/li&gt;\n                    &lt;li className=\"nav-item\"&gt;\n                        &lt;a className=\"nav-link\" href=\"#\"&gt;Features&lt;/a&gt;\n                    &lt;/li&gt;\n                    &lt;li className=\"nav-item\"&gt;\n                        &lt;a className=\"nav-link\" href=\"#\"&gt;Pricing&lt;/a&gt;\n                    &lt;/li&gt;\n                &lt;/ul&gt;\n            &lt;/div&gt;\n        &lt;/nav&gt;\n      );\n   }\n}\n\nexport default Nav;\n</pre><p>Ở đây mình tạo một component tên là&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Nav</code>&nbsp;có chức năng là để làm menu cho page của mình.</p><p><br></p><p><strong>Chú ý:</strong></p><p>Theo quy định của Reactjs thì không có chứa 2 thẻ html ngang hàng với nhau. Nếu có 2 thẻ ngang hàng nhau thì cần có thẻ cha bọc bên ngoài.</p><p><br></p><p><em>Ví dụ:</em></p><pre class=\"ql-syntax\" spellcheck=\"false\">class NewComponent extends Component {\n   render() {\n      return (\n        &lt;div className=\"alert alert-primary\" role=\"alert\"&gt;\n          This is a primary alert—check it out!\n        &lt;/div&gt;\n        &lt;div className=\"alert alert-secondary\" role=\"alert\"&gt;\n          This is a secondary alert—check it out!\n        &lt;/div&gt;\n      );\n   }\n}\n\nexport default NewComponent;\n</pre><p>Cách viết trên là 1 ví dụ sai về lỗi mà mình nêu trên. Để có 1 component chuẩn thì trong trường hợp này mình sẽ gộp 2 thẻ div ngang hàng vài 1 div lớn.</p><pre class=\"ql-syntax\" spellcheck=\"false\">class NewComponent extends Component {\n   render() {\n      return (\n         &lt;div&gt;\n            &lt;div className=\"alert alert-primary\" role=\"alert\"&gt;\n              This is a primary alert—check it out!\n            &lt;/div&gt;\n            &lt;div className=\"alert alert-secondary\" role=\"alert\"&gt;\n              This is a secondary alert—check it out!\n            &lt;/div&gt;\n         &lt;/div&gt;\n      );\n   }\n}\n\nexport default NewComponent;\n</pre><p><strong>Tips:</strong>&nbsp;Nếu bạn dùng&nbsp;<a href=\"https://magic.reactjs.net/htmltojsx.htm\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(43, 109, 173);\">Link convert</a>&nbsp;này thì nó sẽ tự động thêm 1 div lớn cho mình.&nbsp;</p><p><br></p><p>Có 4 cách để chúng ta có thể tạo nên 1 component:</p><ul><li>Kiểu hàm function bình thường</li><li>Function không tên. Anonymous function</li><li>Arrow function</li><li>Class</li></ul><p>Tùy vào mục đích ta sẽ sử dụng các cách cho hợp lý.</p><p>Thông thường những component có chức năng riêng sẽ có một class riêng và ta sẽ sử dụng cách 4 để tạo nên một component.</p><h3><br></h3><h3><strong>3. Props</strong></h3><p>Props là viết tắt của từ Properties, là một thuộc tính của component. Chúng ta có thể thay đổi props của một component bằng cách truyền một tham số vào.</p><p><em>Ví dụ:</em></p><pre class=\"ql-syntax\" spellcheck=\"false\"> &lt;Company name=\"framgia\" /&gt;\n</pre><p>Ở đây mình tự đinh nghĩa 1 component có tên là&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Company</code>&nbsp;và truyền props&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">name</code>&nbsp;vào.</p><p>Cụ thể hơn:</p><p><br></p><p>ta sẽ làm như sau: (ở đây mình khai báo theo kiểu function)</p><pre class=\"ql-syntax\" spellcheck=\"false\">function Company (props) {\n	return (\n		&lt;div&gt; {props.name} &lt;/div&gt;\n    )\n}\n// gọi như sau:\n&lt;Company name=\"framgia\"/&gt;\n</pre><p><code style=\"background-color: rgb(238, 238, 238); color: inherit;\">props</code>&nbsp;ở đây là từ khóa và không thay đổi nhé.</p><p><br></p><p>Qua đây mình đã nói vễ những kiến thức cơ bản nhất về reactjs để chúng ta có thể bắt đầu với nó. Hẹn gặp lại các bạn trong những bài sau nhé.</p>', 'Chào các bạn, hôm nay mình sẽ giới thiệu cho các bạn những kiến thức cần có để bắt đầu học Reactjs. Điển hình là JSX, Component, Props.\n  Reactjs là một thư viện javascript mã nguồn mở giúp cho việc xây dựng giao diện người dùng có thể tái sử dụng. Hiện nay, thư viện này nhận được rất nhiều sự quan tâm đến từ cộng đồng. Nó đang được bảo trì (maintain) bởi Facebook và Instagram, cũng với sự đóng góp của cộng đồng các lập trình viên giỏi trên Thế giới.', 'Reactjs-Những-kiến-thức-cơ-bản-để-bắt-đầu-2023-08-13T22:26:01.580459', 'Reactjs Những kiến thức cơ bản để bắt đầu', 2, 1);
INSERT INTO `articles` VALUES (3, '2023-08-13 22:22:54', '2023-08-13 22:26:57', '<p>Chúng ta thường biết tới&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Java</code>&nbsp;thông qua khái niệm hướng đối tượng như sau:</p><pre class=\"ql-syntax\" spellcheck=\"false\">String str = \"Hello Loda\";\nstr.toUpperCase(); // Chúng ta gọi hàm toUpperCase() thông qua toán tử \".\"\n// Mọi thứ trong đối tượng là khép kín, chúng ta phải gọi thông qua hàm public\n</pre><p>Hoặc</p><pre class=\"ql-syntax\" spellcheck=\"false\">public class Girl {\n    String name;\n    int age;\n    int atk;\n    int agi;\n    int def;\n    // ... Và 1000 thuộc tính khác\n\n    public static void main(String[] args) {\n        Girl girl = new Girl();\n        // Chúng ta thường phải nhớ tên thuộc tính để gọi nó ra\n        girl.name = \"Ngoc Trinh\";\n\n        // Giá sử class này có 100 thuộc tính là String. \n        // Bạn muốn set giá trị của tất cả trường String là \"Ngoc Trinh\"\n        // Bạn sẽ rất bối rối vs việc gọi từng thuộc tính bằng việc \".{tên thuộc tính}\" như này.\n\n        // Có cách nào cho code duyệt tìm toàn bộ thuộc tính, cái nào là String thì đổi nó thành \"Ngoc Trinh\"?\n    }\n}\n\n</pre><p>Đúng vậy, khi chúng ta muốn gọi tên thuộc tính, mà lại không muốn gõ&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">.</code>&nbsp;và nhớ ra tên thuộc tính, thì làm như nào?</p><p>Bây giờ, chúng ta phải tiếp cận từ góc nhìn khác. Chúng ta sẽ ước mình có thể&nbsp;<strong>duyệt hết tất cả</strong>&nbsp;các thuộc tính của 1 class bằng vòng lặp. Rồi check xem thuộc tính có là&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">String</code>&nbsp;không? nếu có thì gán giá trị mới là \"Ngoc Trinh\"!</p><p>Để làm được điều này, chúng ta cần đào sâu vào&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Class</code>&nbsp;và phá vỡ giới hạn của java truyền thống. Đây là lúc&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Java Reflection</code>&nbsp;(Sự phản chiếu) vào trận.</p><h3><br></h3><h3><strong>Java Reflection</strong></h3><p><code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Java Reflecion</code>&nbsp;cho phép bạn đánh giá, sửa đổi cấu trúc và hành vi của một đối tượng tại thời gian chạy (runtime) của chương trình. Đồng thời nó cho phép bạn truy cập vào các thành viên private (private member) tại mọi nơi trong ứng dụng, điều này không được phép với cách tiếp cận truyền thống.</p><p><br></p><h3><strong>Lấy ra Thuộc tính (Field)</strong></h3><p>Quay trở lại ví dụ trên, Chúng ta sẽ lấy ra toàn bộ thuộc tính của&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Girl</code>. Tìm xem cái nào tên&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">name</code>&nbsp;và bổ sung giá trị mới cho nó.</p><pre class=\"ql-syntax\" spellcheck=\"false\">\npublic class Girl {\n    private String name;\n\n    public Girl() {\n\n    }\n\n    public Girl(String name) {\n        this.name = name;\n    }\n\n    public void setName(String name){\n        this.name = name;\n    }\n\n    @Override\n    public String toString() {\n        return \"Girl{\" +\n               \"name=\'\" + name + \'\\\'\' +\n               \'}\';\n    }\n\n    public static void main(String[] args) throws Exception {\n        Girl girl = new Girl(); // KHởi tạo đối tượng Girl\n        girl.setName(\"Ngoc trinh\");\n\n        // Lay ra tat ca field cua object\n        // Chỉ để bạn xem ví dụ thôi, bỏ qua phần này nhé!\n        for(Field field : girl.getClass().getDeclaredFields()){\n            System.out.println();\n            System.out.println(\"Field: \" +field.getName());\n            System.out.println(\"Type: \" +field.getType());\n        }\n\n        // PHẦN CHÍNH\n        Field nameField = girl.getClass().getDeclaredField(\"name\"); // Lấy ra field có tên \"name\" (nếu không tìm thấy, nó sẽ bắn NoSuchFieldException)\n        nameField.setAccessible(true); // Cho phép truy cập tạm thời. (Vì nó đang là Private mà)\n\n        // Bây giờ cái \"nameField\" đại diện cho thuộc tính \"name\" của mọi object có class Girl.\n        nameField.set(girl, \"Bella\"); // thay giá trị mới của `girl` bằng nameField.\n        \n\n        System.out.println(girl);\n    }\n}\n// Output:\n// Field: name\n// Type: class java.lang.String\n// Girl{name=\'Bella\'}\n</pre><h3><br></h3><h3><strong>Lấy ra Hàm (Method)</strong></h3><p>Vấn đề đặt ra, giống với&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">field</code>. Chúng ta cũng sẽ có nhu cầu duyệt tìm một&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">method</code>&nbsp;nào đó và sử dụng nó:</p><pre class=\"ql-syntax\" spellcheck=\"false\">\npublic static void main(String[] args) throws Exception {\n    Class&lt;Girl&gt; girlClass = Girl.class;\n\n    // Su dung getDeclaredMethods de lay ra nhung method cua class va cha no.\n    Method[] methods = girlClass.getDeclaredMethods();\n    for(Method method : methods){\n        System.out.println();\n        System.out.println(\"Method: \" + method.getName());\n        System.out.println(\"Parameters: \" + Arrays.toString(method.getParameters()));\n    }\n\n    // Lay ra method ten la setName va co 1 tham so truyen vao -&gt; \n    // =&gt; chính là: setName(String name)\n    Method methodSetName = girlClass.getMethod(\"setName\", String.class);\n    // Bây giờ methodSetName sẽ đại diện cho method setName(String name) của mọi object có class là Girl\n\n\n    Girl girl = new Girl(); // Tạo ra đối tượng Girl\n\n    // Thực hiện hàm setName() trên đối tượng girl, giá trị truyền vào là \"Ngoc Trinh\"\n    methodSetName.invoke(girl, \"Ngoc Trinh\");\n    System.out.println(girl);\n}\n</pre><h3><br></h3><h3><strong>Lấy ra Constructor</strong></h3><p>Lấy ra hàm khởi tạo của một class. Từ đó cho phép chúng ta cách tạo ra đối tượng từ theo một cách khác, thay vì&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">new Class()</code>&nbsp;như bình thường</p><pre class=\"ql-syntax\" spellcheck=\"false\">public static void main(String[] args) {\n    Class&lt;Girl&gt; girlClass = Girl.class;\n    System.out.println(\"Class: \" + girlClass.getSimpleName());\n    System.out.println(\"Constructors: \" + Arrays.toString(girlClass.getConstructors())); // Lấy ra toàn bộ Constructor của class này\n    try {\n        // Tạo ra một object Girl từ class. (Khởi tạo không tham số)\n        Girl girl1 = girlClass.newInstance();\n        System.out.println(\"Girl1: \" + girl1);\n\n        // Lấy ra hàm constructor với tham số là 1 string \n        // Chính là -&gt; public Girl(String name) {}\n        Constructor&lt;Girl&gt; girlConstructor = girlClass.getConstructor(String.class);\n        Girl girl2 = girlConstructor.newInstance(\"Hello\");\n\n        System.out.println(\"Girl2: \" + girl2);\n    } catch (Exception e) {\n        // Exception xay ra khi constructor khong ton tai hoac tham so truyen vao khong dung\n        e.printStackTrace();\n    }\n}\n</pre><h3><br></h3><h3><strong>Lấy ra Annotation trên Field, Method, Class</strong></h3><p>Đúng vậy, đây cũng chính là một trong những phần quan trọng bậc nhất của&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Java Reflection</code>. Cho phép chúng ta kiểm tra&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Class</code>&nbsp;hiện tại đang được chú thích bởi những&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Annotation</code>&nbsp;nào.</p><pre class=\"ql-syntax\" spellcheck=\"false\">@SuppressWarnings(\"deprecation\")\n@Deprecated\npublic class Girl {\n    private String name;\n\n    public Girl() {\n\n    }\n\n    public Girl(String name) {\n        this.name = name;\n    }\n\n    @Nullable\n    public void setName(String name){\n        this.name = name;\n    }\n\n    @Override\n    public String toString() {\n        return \"Girl{\" +\n               \"name=\'\" + name + \'\\\'\' +\n               \'}\';\n    }\n\n    public static void main(String[] args) {\n        Class&lt;Girl&gt; girlClass = Girl.class;\n        System.out.println(\"Class: \"+girlClass.getSimpleName()); // Lấy ra tên Class\n        for(Annotation annotation : girlClass.getDeclaredAnnotations()){\n            System.out.println(\"Annotation: \" + annotation.annotationType()); // Lấy ra tên các Annatation trên class này\n        }\n\n        for(Method method: girlClass.getDeclaredMethods()){ // Lấy ra các method của class\n            System.out.println(\"\\nMethod: \" + method.getName()); //Tên method\n            for(Annotation annotation : method.getAnnotations()){\n                System.out.println(\"Annotation: \" + annotation.annotationType()); // Lấy ra tên các Annatation trên method này\n            }\n        }\n    }\n}\n</pre><p>Với cách này, bạn hoàn toàn có thể tự tạo ra 1&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Annotation</code>&nbsp;và xử lý nó</p><p><br></p><p>Bài viết tới đây kết thúc, bạn đã có thể sử dụng&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Java Reflection</code>&nbsp;xoành xoạch rồi đó, chúc bạn học tập tốt ahoho!</p>', 'Java Relection là một core package trong thư viện chuẩn của Java. Mục đích của nó là cho phép chúng ta truy cập vào gần như mọi thứ bên trong đối tượng. \"Dưới một góc độ khác\"!', '「Java」Hướng-dẫn-Java-Reflection-1691940174416', '「Java」Hướng dẫn Java Reflection', 2, 1);
INSERT INTO `articles` VALUES (4, '2023-08-13 22:24:16', '2023-08-13 22:26:56', '<h3><strong>Vì sao bạn nên học Java?</strong></h3><p>Trước khi nói&nbsp;<strong>Spring Boot</strong>, chúng ta nói về nền tảng của nó, chính là&nbsp;<strong>Java</strong>.</p><p><strong>Java</strong>&nbsp;ra đời năm 1991, tới nay thì đã gần 30 năm rồi. Và có một điều mà có lẽ ít lập trình viên biết, đó là tính tới năm 2018, nó vẫn là&nbsp;<strong>ngôn ngữ phổ biến nhất thế giới</strong>.</p><p>Có thể thời thế thế thay đổi, có thể&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Python</code>,&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Javascript</code>&nbsp;đang vươn lên mạnh mẽ, nhưng có một thứ gọi là&nbsp;<strong>Legacy</strong>.</p><blockquote><strong><em>Java là Legacy</em></strong></blockquote><p>Tức là nó không thể bị loại bỏ hay thay thế, và doanh nghiệp trong năm nay, năm sau, và 5 năm nữa, họ vẫn sẽ và tiếp tục dùng&nbsp;<strong>Java</strong>.</p><p>Các trường đại học vẫn sẽ chọn dạy&nbsp;<strong>Java</strong>&nbsp;như một tiêu chuẩn về khái niệm hướng đối tượng.</p><p>Mình nói như ở trên là để cho những bạn mới tiếp cận tới ngôn ngữ này hoặc những bạn mới vào nghề có thể hiểu rõ tầm quan trọng của Java, và tất nhiên là lương của bạn cũng sẽ rất cao 😂 (yếu tố chính)</p><p>Còn cá nhân mình, thì mình thích cái cú pháp của&nbsp;<strong>Java</strong>, nó rõ ràng, dễ hiểu và dễ đọc. Ngoài ra, khái niệm OOP trong Java là chuẩn mực, kế thừa và interface đã khiến các dòng code trở nên \"yêu\" hơn và dễ dàng mở rộng hơn.</p><p>Khi được chạm tay vào những đoạn code siêu sao, cảm giác cực kì, cực kì phê. Mặc dù quy tắc của&nbsp;<strong>Java</strong>&nbsp;rất đơn giản và không nhiều biến thể được hỗ trợ bởi thông dịch như&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Javascript</code>&nbsp;hay&nbsp;<code style=\"background-color: rgb(238, 238, 238); color: inherit;\">Python</code>, nhưng bạn vẫn không thể nào hết ngạc nhiên với&nbsp;<strong>Java</strong>&nbsp;được, bạn sẽ còn trầm trồ dù học nó bao lâu đi nữa.</p><p><strong>Java</strong>&nbsp;được sử dụng trong nhiều lĩnh vực khác nhau, có thể là Frontend, có thể là Backend, có thể viết Game, Desktop App, Mobile App, xử lý dữ liệu lớn, Microservices, ML, AI, v.v.. nó len lỏi từng ngóc nghách của lập trình. Vì vậy, có kiến thức nền tảng là Java thì bạn rất rất có lợi, làm được rất nhiều thứ, mà dù cho có không sử dụng đến, nó vẫn sẽ giúp bạn học được các ngôn ngữ khác nhanh hơn.</p><p>Với một cộng đồng cực kì đông đảo và ngôn ngữ luôn cải thiện trong các version JDK về sau, mình tỉn rằng&nbsp;<strong>Java</strong>&nbsp;vẫn luôn là một khởi điểm tốt cho mọi lập trình viên.</p><h3><strong>Vì sao bạn nên học Spring?</strong></h3><p>Vì nó là Framework, nên dù bạn có học nó, hay không học nó, bạn vẫn biết code&nbsp;<strong>Java</strong>&nbsp;thôi 😆</p><p>Nhưng nếu không học&nbsp;<strong>Spring</strong>&nbsp;hay&nbsp;<strong>Spring Boot</strong>&nbsp;thì bạn đã bỏ lỡ đi những điều thú vị mà Java có thể làm được. Chưa kể tới bỏ lỡ hàng tỉ cơ hội về nghề nghiệp, lương tỉ đô 😂 mặc dù mình biết nghề nghiệp cũng là phụ thôi, phải không các bạn, chúng ta làm vì đam mê! 😗</p><p><strong>Spring</strong>&nbsp;là một framework java mãnh mẽ và phổ biến nhất hiện nay dành cho doanh nghiệp. Nó giúp rút ngắn thời gian lập trình và test, giảm sự rườm rà trong code, giảm thiểu bottleneck.</p><p>Hệ sinh thái&nbsp;<strong>Spring</strong>&nbsp;hỗ trợ mọi layer từ frontend, backend, persistence, third-paty, hỗ trợ mọi kiến trúc từ củ chuối tới microservice, code thì lại dễ dàng, nâng cao hiệu năng của lập trình viên. Nó là ánh sáng le lói chọc xuyên đêm đen, giúp cuộc đời của một Java Developer có chút hi vọng lay lắt, và tiếp túc sống (nghe cứ sai sai vấn đề 😂)</p><p>Còn rất nhiều thứ có thể kể về&nbsp;<strong>Spring</strong>&nbsp;nhưng vì bạn chưa biết thực ra nó là gì, nên có kể nữa cũng không tác dụng. Bây giờ bạn chỉ cần biết&nbsp;<strong>Spring</strong>&nbsp;và thằng con của nó (vâng, đặc biệt là thằng con của nó),&nbsp;<strong>Spring Boot</strong>&nbsp;là thứ bạn&nbsp;<strong>PHẢI HỌC</strong>&nbsp;khi tới với&nbsp;<strong>Java</strong>.</p><p><br></p>', 'Java ra đời năm 1991, tới nay thì đã gần 30 năm rồi. Và có một điều mà có lẽ ít lập trình viên biết, đó là tính tới năm 2018, nó vẫn là ngôn ngữ phổ biến nhất thế giới.', 'Tại-sao-nên-học-Java-và-Spring?-1691940256482', 'Tại sao nên học Java và Spring?', 2, 1);

-- ----------------------------
-- Table structure for available_tags
-- ----------------------------
DROP TABLE IF EXISTS `available_tags`;
CREATE TABLE `available_tags`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_pinned` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of available_tags
-- ----------------------------
INSERT INTO `available_tags` VALUES (1, 'java', NULL);
INSERT INTO `available_tags` VALUES (2, 'html', NULL);
INSERT INTO `available_tags` VALUES (3, 'css', NULL);
INSERT INTO `available_tags` VALUES (4, 'go', NULL);
INSERT INTO `available_tags` VALUES (5, 'spring-boot', NULL);
INSERT INTO `available_tags` VALUES (6, 'reactjs', NULL);
INSERT INTO `available_tags` VALUES (7, 'javascript', NULL);
INSERT INTO `available_tags` VALUES (8, 'fix-bug', NULL);
INSERT INTO `available_tags` VALUES (9, 'hỏi đáp', NULL);
INSERT INTO `available_tags` VALUES (10, 'docker', NULL);
INSERT INTO `available_tags` VALUES (11, '.net', NULL);
INSERT INTO `available_tags` VALUES (12, 'c#', NULL);
INSERT INTO `available_tags` VALUES (13, 'nosql', NULL);
INSERT INTO `available_tags` VALUES (14, 'sql', NULL);
INSERT INTO `available_tags` VALUES (15, 'tư vấn', NULL);
INSERT INTO `available_tags` VALUES (16, 'tâm sự', NULL);
INSERT INTO `available_tags` VALUES (17, 'chém gió', NULL);
INSERT INTO `available_tags` VALUES (18, 'thông báo', 1);
INSERT INTO `available_tags` VALUES (19, 'nội quy', 1);
INSERT INTO `available_tags` VALUES (20, 'nổi bật', 1);

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `article_id` bigint NOT NULL,
  `author_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKk4ib6syde10dalk7r7xdl0m5p`(`article_id` ASC) USING BTREE,
  INDEX `FKn2na60ukhs76ibtpt9burkm27`(`author_id` ASC) USING BTREE,
  CONSTRAINT `FKk4ib6syde10dalk7r7xdl0m5p` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKn2na60ukhs76ibtpt9burkm27` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `article_id` bigint NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKg5bjjgr1bd2g8guv8125gts3s`(`article_id` ASC) USING BTREE,
  INDEX `FKk7du8b8ewipawnnpg76d55fus`(`user_id` ASC) USING BTREE,
  CONSTRAINT `FKg5bjjgr1bd2g8guv8125gts3s` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKk7du8b8ewipawnnpg76d55fus` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of favorites
-- ----------------------------

-- ----------------------------
-- Table structure for follows
-- ----------------------------
DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `followee_id` bigint NOT NULL,
  `follower_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_follow_followee_pair_must_be_unique`(`followee_id` ASC, `follower_id` ASC) USING BTREE,
  INDEX `FKjnqt4f5bti6niw7afunse4de7`(`follower_id` ASC) USING BTREE,
  CONSTRAINT `FKa58cq5w7xon5bn0m7e7n6lbwt` FOREIGN KEY (`followee_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKjnqt4f5bti6niw7afunse4de7` FOREIGN KEY (`follower_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of follows
-- ----------------------------

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `to_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `from_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `post_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `comment_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'ROLE_SUPER_ADMIN');
INSERT INTO `roles` VALUES (2, 'ROLE_ADMIN');
INSERT INTO `roles` VALUES (3, 'ROLE_USER');

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `article_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK2b4i2k07k9ecvfc94946uhat8`(`article_id` ASC) USING BTREE,
  CONSTRAINT `FK2b4i2k07k9ecvfc94946uhat8` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tags
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime NULL DEFAULT NULL,
  `updated_at` datetime NULL DEFAULT NULL,
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `image` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `otp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, '2023-07-07 08:16:11', '2023-07-29 14:09:06', 'sugoiiii', 'tungxtnd0@gmail.com', 'https://wallpapers.com/images/hd/hacker-pictures-4xhiey685feo8stu.jpg', '12345678', 'tungdeptraii', 'ACTIVE', '373745');
INSERT INTO `users` VALUES (2, '2023-08-13 22:14:41', '2023-08-13 22:19:28', 'paolem master reactjs', 'paolem@mail1s.edu.vn', 'https://www.iconpacks.net/icons/1/free-user-icon-295-thumb.png', '12345678', 'paolem_reactjs', 'ACTIVE', '356555');

-- ----------------------------
-- Table structure for users_roles_nn
-- ----------------------------
DROP TABLE IF EXISTS `users_roles_nn`;
CREATE TABLE `users_roles_nn`  (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FK3l81w80fhpyeav0l6ual9uvw9`(`role_id` ASC) USING BTREE,
  CONSTRAINT `FK3l81w80fhpyeav0l6ual9uvw9` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKasbhg5qkd4qqyog36gtqbjp52` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users_roles_nn
-- ----------------------------
INSERT INTO `users_roles_nn` VALUES (1, 1);
INSERT INTO `users_roles_nn` VALUES (1, 2);
INSERT INTO `users_roles_nn` VALUES (1, 3);
INSERT INTO `users_roles_nn` VALUES (2, 3);

SET FOREIGN_KEY_CHECKS = 1;
