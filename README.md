# EmailSentimentClustering

Code implementation for sentiment clustering with topic and temporal information for Enron Email subset. Revised clustering method based on DBSCAN algorithm [1]. Features extracted using Opinion word list developed by [2], containing 2,046 positive words and phases, and 4,833 negative words and phases.. If you make use of this implementation for your research, please cite the following paper:

  'Liu, S., Lee, I., & Cai, G. (2016). Sentiment Clustering with Topic and Temporal Information from Large Email Dataset. In Proceedings of the 30th Pacific Asia Conference on Language, Information and Computation: Posters (pp. 363-371).'
  
Notes:
  1. run SentimentClustering.java
  2. change input and output file directory
  3. input format: <id, topic, timestamp, [features]>. Eg. <1633, Other, 980467200000, [rich, -drag, -drag, -difficult, consistent]>
  4. sample output: 
      <141004:General Operation-[sufficient, decent, like, great]: SP>
      <54456:Other-[-poor, -wretched]: SN>
      <140992:Private Issue-[-break, helpful, easier]: P>

References:
  1. Martin Ester, Hans-Peter Kriegel, J¨org Sander, and Xiaowei Xu. 1996. A density-based algorithm for discovering clusters in large spatial databases with noise. InKdd, (Vol. 96, No. 34, pp. 226-231).
  2. Bing Liu, Xiaoli Li, Wee Sun Lee, and Philip S. Yu. 2004. Text classification by labeling words. InAAAI, (Vol. 4, pp. 425-430).
