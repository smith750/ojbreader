(ns ojbreader.core)
(require '[clojure.xml :as xml])
(require '[clojure.java.io :as io])

(def BASE-KFS-DIR "c:\\java\\projects\\release-5-cgb\\work\\src\\org\\kuali\\kfs\\")

(defn read-ojb-file [ojb-file-name]
	(let [parsed-xml (xml/parse (io/file ojb-file-name))]
		(doseq 
			[x (xml-seq parsed-xml)
				:when (= :class-descriptor (:tag x))]
			(println (str (:class (:attrs x)) ", " (:table (:attrs x)) ))
			(doseq
				[y (:content x)
					:when (and 
							(= :field-descriptor (:tag y)) 
							(contains? (:attrs y) :primarykey) 
							(= "true" (:primarykey (:attrs y)))
							)]
					(println (str "\t" (:name (:attrs y)) ", " (:column (:attrs y))) )
			)
		)
	)
)

(defn find-ojb-files [directory-name]
	(doseq
		[f (file-seq (io/file directory-name))
			:when (re-matches #"^ojb-[^\.]+\.xml$" (.getName f))
			]
		(read-ojb-file (.getAbsolutePath f))
	)
)

(defn -main [& args]
	(find-ojb-files BASE-KFS-DIR)
)