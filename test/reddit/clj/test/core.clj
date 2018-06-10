(ns reddit.clj.test.core
  (:use [reddit.clj.core])
  (:use [clojure.test]))

(def testuser "redditclj")
(def testpasswd "redditclj")

(def r (login testuser testpasswd))

(deftest test-login
  (is (not (nil? r))))

(deftest test-subreddits
  (let [rdts (reddits r "Clojure")]
    (is (= 25 (count rdts)))
    (is (= "Clojure" (:subreddit (first rdts))))))

(deftest test-domainreddits
  (let [rdts (domain r "sunng.info")]
    (is (< 0 (count rdts)))
    (is (= "sunng.info" (:domain (first rdts))))))

(deftest test-info
  (let [rdts (info r "https://www.imgur.com")]
    (is (< 0 (count rdts)))
    (is (= "imgur.com" (:domain (first rdts))))))

(deftest test-me
  (let [userinfo (me r)]
    (is (= testuser (:name userinfo)))))

(deftest test-search
  (let [results (search r "java")]
    (is (not (empty? results)))))

(deftest test-mine
  (let [rdts (mine r)]
    (is (<= 1 (count rdts)))))

(deftest test-votes
  (is (true? (vote-up r "t3_8q221b"))))

(deftest test-thing-type
  (is (= "comment" (thing-type "t1_c26zchy")))
  (is (= "link" (thing-type "t3_iupme")))
  (is (= "subreddit" (thing-type "t5_2fwo")))
  (is (nil? (thing-type "ak47"))))

(deftest test-user-liked
  (is (> (count (user-liked r "redditclj")) 0)))
