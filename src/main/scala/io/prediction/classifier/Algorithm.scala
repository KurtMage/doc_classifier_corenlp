package io.prediction.classifier

import edu.stanford.nlp.classify.LinearClassifierFactory
import io.prediction.controller.P2LAlgorithm
import org.apache.spark.SparkContext

class Algorithm(val ap: AlgorithmParams)
  extends P2LAlgorithm[PreparedData, Model, Query, PredictedResult] {

  def train(sc: SparkContext, data: PreparedData): Model = {
    val linearClassifierFactory =
      new LinearClassifierFactory[String, String](1e-4, false, 1 , ap.sigma, 0.01, 15)

    val classifier = linearClassifierFactory.trainClassifier(data.dataset)

    Model(classifier)
  }

  def predict(model: Model, query: Query): PredictedResult = {

    val datum = ClassifierUtil.makeDatumFromStrings(Array("", "", query.text))
    val label = model.classifier.classOf(datum)

    PredictedResult(label)
  }
}
