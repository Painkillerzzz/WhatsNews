//import com.orm.SchemaGenerator;
//import com.orm.SugarContext;
//import com.orm.SugarDb;
//
//public class NewsDatabaseHelper {
//
//    public static void recreateDatabase() {
//        // 删除数据库
//        SugarDb sugarDb = new SugarDb(SugarContext.getSugarContext());
//        SchemaGenerator schemaGenerator = new SchemaGenerator(SugarContext.getSugarContext());
//        schemaGenerator.deleteTables(sugarDb.getDB());
//
//        // 关闭数据库连接
//        sugarDb.getDB().close();
//
//        // 重新初始化SugarORM
//        SugarContext.init(SugarContext.getSugarContext());
//    }
//}
