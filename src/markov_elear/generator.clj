(ns markov-elear.generator
  (:require [clojure.string :refer [split]])
  (:require [clojure.set :refer [union]])
  (:require [clojure.core :refer [partition-all subvec merge-with]]))

(defn text->words [text] (split text #"\s+"))

(defn words->transitions [words] (partition-all 3 1 words))

(defn fmt-trans [[a b c]] {[a b] (if (= c nil) #{} #{c} )})

(defn f [acc e]
  (let [e (fmt-trans e)]
    (merge-with union e acc)))

(defn word-chain [word-transitions] (reduce f {} word-transitions))

(defn word-chain-one-liner [word-transitions]
  (apply merge-with union (map fmt-trans word-transitions)))

(defn text->word-chain [text]
  (word-chain (words->transitions (text->words text))))
