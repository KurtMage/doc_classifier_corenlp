package io.prediction.classifier

import edu.stanford.nlp.classify.Dataset
import io.prediction.controller.{EmptyActualResult, EmptyEvaluationInfo, PDataSource}
import io.prediction.data.storage.Storage
import org.apache.spark.SparkContext

class DataSource(val dsp: DataSourceParams) extends PDataSource[
  TrainingData,
  EmptyEvaluationInfo,
  Query,
  EmptyActualResult] {

  override def readTraining(sc: SparkContext): TrainingData = {
    val events =
      Storage.getPEvents().find(appId = dsp.appId, entityType = Some("phrase"))(sc).collect()

    val dataset = new Dataset[String, String]
    events.foreach { event =>
      val strings = Array(
        event.properties.get[String]("phrase"),
        event.entityId,
        event.properties.get[String]("Interest"))

      val datum = ClassifierUtil.makeDatumFromStrings(strings)
      dataset.add(datum)
    }

    TrainingData(dataset)
  }
}

