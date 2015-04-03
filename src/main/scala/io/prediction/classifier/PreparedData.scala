package io.prediction.classifier

import edu.stanford.nlp.classify.GeneralDataset

case class PreparedData(dataset: GeneralDataset[String, String]) extends Serializable
