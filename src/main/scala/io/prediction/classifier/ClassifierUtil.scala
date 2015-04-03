package io.prediction.classifier

import java.util.Properties

import edu.stanford.nlp.classify.ColumnDataClassifier
import edu.stanford.nlp.ling.Datum

object ClassifierUtil {
  private val properties = new Properties()
  properties.setProperty("2.useSplitWords", "true")
  properties.setProperty(
    "2.splitWordsTokenizerRegexp",
    "[\\p{L}][\\p{L}0-9]*|(?:\\$ ?)?[0-9]+(?:\\.[0-9]{2})?%?|\\s+|[\\x80-\\uFFFD]|.")
  properties.setProperty("2.splitWordsIgnoreRegexp", "\\s+")

  private val classifier = new ColumnDataClassifier(properties)

  def makeDatumFromStrings(strings: Array[String]): Datum[String, String] = {
    classifier.makeDatumFromStrings(strings)
  }
}
