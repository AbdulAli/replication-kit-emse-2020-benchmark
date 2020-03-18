library(dplyr)
datagap <- read.csv("output.csv")
dataleave <- read.csv("output_leaveone.csv")

df<-summarise_at(group_by(data,technique,configuration,windowSize,time),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

data<-df

# just slice the data into x
x <- aggregate(data[,c("fscore","mcc","roc","gscore")], 
               by=list(technique = data$technique,
                       configuration = data$configuration,time = data$time,
                       windowSize  = data$windowSize), FUN=mean)

# Calculating ranks without mean rank formula
check <- x %>% group_by(configuration,time,windowSize) %>% 
  mutate(rankF = dense_rank(-fscore))  %>% 
  mutate(rankMCC = dense_rank(-mcc)) %>% mutate(rankROC = dense_rank(-roc)) %>% mutate(rankG = dense_rank(-gscore))

# Calculating 1 - ((#techniquesRankedLower)/4)
check$rankFHM <- 1-((check$rankF-1)/4)
check$rankMCCHM <- 1-((check$rankMCC-1)/4)
check$rankROCHM <- 1-((check$rankROC-1)/4)
check$rankGHM <- 1-((check$rankG-1)/4)

# Taking mean of rankFHM,rankMCCHM,rankROCHM,rankGscore to calculate MeanRankScore
check$MeanRank <- rowMeans(check[,13:16])

# Calculating Ranks of MeanRankScore in FinalRank
check <- check %>% group_by(configuration,time,windowSize) %>% mutate(FinalRank = dense_rank(-MeanRank))

#Calculating Ranks of each technique per configuration
fR <- summarise_at(group_by(check,technique,configuration),vars(MeanRank),funs(mean(.,na.rm=TRUE)))
finalRanks <- fR %>% group_by(configuration) %>% mutate(FinalRank = dense_rank(-MeanRank))

#Calculating Ranks of each technique for all configs
fR <- summarise_at(group_by(check,technique),vars(MeanRank),funs(mean(.,na.rm=TRUE)))
finalRanks <- fR %>% mutate(FinalRank = dense_rank(-MeanRank))

# Calculating SD of Final Ranks per technique and configuration
check <- check %>% group_by(technique,configuration) %>% mutate(rankSD = sd(FinalRank))

# Slicing Distinct rows to remove duplicates
result <- unique(check[,c("technique", "configuration", "rankSD")])

#Technqiue and configuration wise mean of F-Score
fscoreMean<-summarise_at(group_by(check,technique,configuration),vars(fscore),funs(mean(.,na.rm=TRUE)))

#Technqiue and configuration wise mean of F-Score, MCC and AUC
meanOfEverything<-summarise_at(group_by(check,technique,configuration),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

meanAllConfigs<-summarise_at(group_by(meanOfEverything,technique),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

#Technqiue and configuration wise sd of F-Score
fscoreSd<-summarise_at(group_by(check,technique,configuration),vars(fscore),funs(sd(.,na.rm=TRUE)))

