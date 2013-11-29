(defproject ttt "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-autoexpect "1.0"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [expectations "1.4.56"]]
  :main ttt.core
  :profiles {:uberjar {:aot :all}})
