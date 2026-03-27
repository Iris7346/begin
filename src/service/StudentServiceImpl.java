package service;


import entity.Student;
import mapper.StudentMapper;

import java.util.List;

/**
 * 业务逻辑层实现类：实现具体业务逻辑，依赖Mapper层进行数据操作
 * 职责：处理数据校验、业务规则，调用Mapper层方法完成数据操作
 */
public class StudentServiceImpl implements StudentService {

    // 依赖Mapper层（通过构造方法注入，解耦且便于测试）
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public String addStudent(Student student) {
        // 1. 校验必填字段
        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            return "新增失败：学号不能为空！";
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return "新增失败：姓名不能为空！";
        }

        // 2. 校验性别合法性
        if (!"男".equals(student.getGender()) && !"女".equals(student.getGender())) {
            return "新增失败：性别必须为'男'或'女'！";
        }

        // 3. 校验年龄范围
        if (student.getAge() < 18 || student.getAge() > 25) {
            return "新增失败：年龄必须在18-25岁之间！";
        }

        // 4. 调用Mapper层新增，返回结果
        boolean isSuccess = studentMapper.add(student);
        return isSuccess ? "新增成功！" : "新增失败：学号已存在！";
    }

    @Override
    public String getStudentByStudentId(String studentId) {
        //TODO 请在此处补全业务逻辑
        //1. 校验学号是否为空
        if(studentId == null || studentId.trim().isEmpty()){//去掉空格后是空字符串
            return "查询失败：学号不能为空！";
        }

        //2. 调用Mapper层查询学生
        Student student = studentMapper.findByStudentId(studentId);

        //3. 判断查询结果并返回
        if(student == null){
            return "查询失败：学号为 " + studentId + " 的学生不存在！";
        }

        //4. 查询成功，返回
        return "查询成功！\n" +
                "学号：" + student.getStudentId() +
                "，姓名：" + student.getName() +
                "，性别：" + student.getGender() +
                "，年龄：" + student.getAge() +
                "，班级：" + student.getClassName() +
                "，专业：" + student.getMajor();
    }

    @Override
    public String getStudentsByClassName(String className) {
        //TODO 请在此处补全业务逻辑
        //1. 校验班级名称
        if(className == null || className.trim().isEmpty()){
            return "查询失败：班级名称不能为空！";
        }

        //2. 调用Mapper层
        List<Student> students = studentMapper.findByClass(className);

        //3. 判断查询结果
        if(students == null || students.isEmpty()){
            return "查询失败：该班级暂无学生！";
        }

        //4. 格式化输出
        String result1 = "共找到 " + students.size() + " 名学生：\n";
        for(int i = 0; i < students.size(); i++){
            Student student = students.get(i);
            result1 = result1 +
                    "学号：" + student.getStudentId() +
                    "，姓名：" + student.getName() +
                    "，性别：" + student.getGender() +
                    "，年龄：" + student.getAge() +
                    "，班级：" + student.getClassName() +
                    "，专业：" + student.getMajor();
            //换行
            if(i < students.size() -1){
                result1 = result1 + "\n";
            }
        }

        //5. 转换成字符串返回
        return result1;
    }

    @Override
    public String getAllStudents() {
        //TODO 请在此处补全业务逻辑
        //1. 调用Mapper层
        List<Student> students = studentMapper.findAll();

        //2. 判断查询结果
        if(students == null || students.isEmpty()){
            return "查询失败：暂无学生数据！";
        }

        //3. 格式化输出
        StringBuilder result = new StringBuilder();

        String result2 = "共找到 " + students.size() + " 名学生：\n";
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            result2 = result2 +
                    "学号：" + student.getStudentId() +
                    "，姓名：" + student.getName() +
                    "，性别：" + student.getGender() +
                    "，年龄：" + student.getAge() +
                    "，班级：" + student.getClassName() +
                    "，专业：" + student.getMajor();
            //换行
            if (i < students.size() - 1) {
                result2 = result2 + "\n";
            }
        }

        //4. 返回结果
        return result2;
    }

    @Override
    public String updateStudent(Student student) {
        //TODO 请在此处补全业务逻辑
        //1. 校验学号是否为空
        if(student.getStudentId() == null || student.getStudentId().trim().isEmpty()){
            return "修改失败：学号不能为空！";
        }

        //2. 检查学号是否存在
        Student exist = studentMapper.findByStudentId(student.getStudentId());
        if(exist == null){
            return "修改失败：学号不存在！";
        }

        //3. 构建新对象
        Student updatedStudent = new Student();//无参构造
        //先复制一份原学生的完整信息，然后只修改想修改的字段
        //设置、获取
        updatedStudent.setStudentId(exist.getStudentId());
        updatedStudent.setName(exist.getName());
        updatedStudent.setGender(exist.getGender());
        updatedStudent.setAge(exist.getAge());
        updatedStudent.setClassName(exist.getClassName());
        updatedStudent.setMajor(exist.getMajor());

        //4. 更新姓名
        if(student.getName() != null && !student.getName().trim().isEmpty()){
            updatedStudent.setName(student.getName().trim());
        }

        //5. 更新性别
        if(student.getGender() != null && !student.getGender().trim().isEmpty()){
            String gender = student.getGender().trim();
            if(!"男".equals(gender) && !"女".equals(gender)){
                return "修改失败：性别必须为'男'或'女'！";
            }
            updatedStudent.setGender(gender);
        }

        //6. 更新年龄
        if(student.getAge() != 0){
            int age = student.getAge();
            if(age < 18 || age > 25){
                return "修改失败：年龄必须在18-25岁之间！";
            }
            updatedStudent.setAge(age);
        }

        //7. 更新班级
        if(student.getClassName() != null && !student.getClassName().trim().isEmpty()){
            updatedStudent.setClassName(student.getClassName().trim());
        }

        //8. 更新专业
        if(student.getMajor() != null && !student.getMajor().trim().isEmpty()){
            updatedStudent.setMajor(student.getMajor().trim());
        }

        //9. 调用Mapper层执行更新
        boolean success = studentMapper.update(updatedStudent);

        //10. 返回结果
        return success ? "修改成功！" : "修改失败！";
    }

    @Override
    public String deleteStudent(String studentId) {
        //TODO 请在此处补全业务逻辑
        //1. 校验是否为空
        if(studentId == null || studentId.trim().isEmpty()){
            return "删除失败：学号不能为空！";
        }

        //2. 检查学生是否存在
        Student exist = studentMapper.findByStudentId(studentId);
        if(exist == null){
            return "删除失败：该学号学生不存在！";
        }

        //3. 调用Mapper层执行删除
        boolean success = studentMapper.delete(studentId);

        //4. 返回结果
        return success ? "删除成功！" : "删除失败！";
    }
}