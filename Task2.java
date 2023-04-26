import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;
import org.apache.spark.sql.functions.*;
import org.apache.spark.sql.types.DataTypes;


public class FactAutoTransform {

    public static void main(String[] args)
    {
            SparkSession spark = SparkSession
                .builder()
                .appName("UserTransactions")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> transactionsDF = spark.read().format("csv").load("transactions.csv");
        Dataset<Row> usersDF = spark.read().format("csv").load("users.csv");

        Dataset<Row> userTransactionsDF = transactionsDF.join(usersDF.withColumnRenamed(user_id, userid),
                        transactionsDF.col("user_id").equalTo(usersDF.col("user_id")),
                        "inner");
        
        Dataset<Row> filteredUserTransactionsDF = UserTransactionsDF.filter(col("is_blocked").equalTo("false").and(col("is_active").equalTo(1)));


        Dataset<Row> aggregatedUserTransactionsDF = filteredUserTransactionsDF
                                .groupBy("transaction_category_id")
                                .agg(sum(col("amount")).alias("sum_amount"),
                                count(col("t.user_id")).alias("num_users")
                                );

        Dataset<Row> sortedUserTransactionsDF  = aggregatedUserTransactionsDF
                            .orderBy(col("sum_amount").desc());

        

    }



}
