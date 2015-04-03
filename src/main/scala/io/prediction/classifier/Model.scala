package io.prediction.classifier

import edu.stanford.nlp.classify.LinearClassifier

case class Model(classifier: LinearClassifier[String, String]) extends Serializable