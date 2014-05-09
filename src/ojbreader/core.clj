(ns ojbreader.core)
(require '[clojure.xml :as xml])
(require '[clojure.java.io :as io])

(def ^:dynamic *BASE-KFS-DIR* "c:\\java\\projects\\release-5-cgb\\work\\src\\org\\kuali\\kfs\\")

(defn read-ojb-file [ojb-file-name]
	(let [parsed-xml (xml/parse (io/file ojb-file-name))]
		(doseq 
			[x (xml-seq parsed-xml)
				:when (= :class-descriptor (:tag x))]
			(println (str (:class (:attrs x)) ", " (:table (:attrs x))))
		)
	)
)

(defn -main [& args]
	(do
		(read-ojb-file (str *BASE-KFS-DIR* "sys\\ojb-sys.xml"))
	)
)