package com.deequtest


import org.apache.spark.sql.{DataFrame, SparkSession}
import com.amazon.deequ.VerificationSuite
import com.amazon.deequ.checks.{Check, CheckLevel, CheckStatus}
import com.amazon.deequ.constraints.ConstraintStatus


class DeequSimpleTest {
  val spark: SparkSession = SparkSession.builder().appName("Deequ").getOrCreate()
  def randomInt1to100 = scala.util.Random.nextInt(100)+1
  def randomString(length: Int) = scala.util.Random.alphanumeric.take(length).mkString
  val df: DataFrame = spark.createDataFrame(
    Seq.fill(100){(randomString(5),randomInt1to100,randomInt1to100,randomInt1to100)}
  ).toDF("i_am_a_string", "val", "another_val", "a_third_val")
  val verificationResult = VerificationSuite()
    .onData(df)
    .addCheck(
      Check(CheckLevel.Error, "Gatcha!")
        .isComplete("i_am_a_string")
        .isUnique("i_am_a_string")
        .isNonNegative("val")
        .isGreaterThan("another_val", "a_third_val")
    ).run()
  if (verificationResult.status == CheckStatus.Success) {
    println("Yay! It is all good bro!")
  } else {
    val resultsForAllConstraints = verificationResult.checkResults
      .flatMap { case (_, checkResult) => checkResult.constraintResults }
    resultsForAllConstraints
      .filter { _.status != ConstraintStatus.Success }
      .foreach { result => println(s"${result.constraint}: ${result.message.get}") }
  }
}

object DeequJob {
  def main(args: Array[String]): Unit = {
    val k = new DeequSimpleTest()
  }
}