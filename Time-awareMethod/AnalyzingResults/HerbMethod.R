nam = read.csv("HerboldMethod/JURECZKO-Nam15.csv")
wat = read.csv("HerboldMethod/JURECZKO-Watanabe08.csv")
cam = read.csv("HerboldMethod/JURECZKO-CamargoCruz09.csv")
ma = read.csv("HerboldMethod/JURECZKO-Ma12.csv")
ama = read.csv("HerboldMethod/JURECZKO-Amasaki15.csv")

techs = c(rep("Nam",length(nam$fscore_DT)),  rep("wat",length(wat$fscore_DT)), 
               rep("cam",length(cam$fscore_DT)), rep("ma",length(ma$fscore_DT)), 
               rep("ama",length(ama$fscore_DT)))

hm <- data.frame(technique = techs, fscore = c(nam$fscore_DT,wat$fscore_DT,cam$fscore_DT,ma$fscore_DT,ama$fscore_DT),
                 mcc = c(nam$mcc_DT,wat$mcc_DT,cam$mcc_DT,ma$mcc_DT,ama$mcc_DT),
                 roc = c(nam$auc_DT,wat$auc_DT,cam$auc_DT,ma$auc_DT,ama$auc_DT),
                 gscore = c(nam$gscore_DT,wat$gscore_DT,cam$gscore_DT,ma$gscore_DT,ama$gscore_DT))

View(hm)


x<-summarise_at(group_by(hm,technique),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

x <- x %>% mutate(rankF = dense_rank(-fscore))  %>% 
  mutate(rankMCC = dense_rank(-mcc)) %>% mutate(rankROC = dense_rank(-roc)) %>% mutate(rankG = dense_rank(-gscore))

x$rankFHM <- 1-((x$rankF-1)/4)
x$rankMCCHM <- 1-((x$rankMCC-1)/4)
x$rankROCHM <- 1-((x$rankROC-1)/4)
x$rankGHM <- 1-((x$rankG-1)/4)

x$MeanRank <- rowMeans(x[,10:13])

x <- x %>% mutate(FinalRank = dense_rank(-MeanRank))



library(orddom)
