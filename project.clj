(defproject ttt "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :repl-port 56759
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-autoexpect "1.0"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [expectations "1.4.56"]]
  :main ttt.console
  :profiles {:uberjar {:aot :all}})