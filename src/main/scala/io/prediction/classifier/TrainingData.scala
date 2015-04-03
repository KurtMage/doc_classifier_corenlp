package io.prediction.classifier

import edu.stanford.nlp.classify.GeneralDataset

case class TrainingData(dataset: GeneralDataset[String, String]) extends Serializable
