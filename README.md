# onsets
This code was used for a paper analyzing the Switchboard corpus. This code is not necessarily intended for public use and is instead to 
document the methodology used in that paper. It depends on the executors repository (https://github.com/jrc436/executors). It needs 
ngram count files for input, such as those that can be produced with SRILM (http://www.speech.sri.com/projects/srilm/). It also depends
on pointwise mutual information to compute the relationship between words, such as what can be produced by Semilar 
(http://www.semanticsimilarity.org/). 
Please email me with any questions at jrcole@psu.edu if you have any questions about setup. 

This code is for modelling speech rates and pauses within the context of the idea of a continuous model of lexical retrieval. It's
associated with the following paper. The goal of the paper was to determine the effect of a word's activation, which is related to 
its retrieval time, on fluent speech.

This project consists of research code designed to model speech rates as a function of lexical memory retrieval. In particular, the goal
is to continuously model the activation of each target word, and investigate how that influences speech rate at that moment. Computing
the activation for each word continuously relies on the time-annotated version of the Switchboard corpus, ngram counts, and pointwise
mutual information. 

This code is not necessarily designed for public use, but to document the process which produced a paper. The paper can be found below.
Please email me at jrcole@psu.edu if you have any questions.

This work was accepted to NAACL 2018! Updated link coming soon.

https://psyarxiv.com/tmxdf/
